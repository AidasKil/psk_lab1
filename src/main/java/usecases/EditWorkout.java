package usecases;

import entities.Exercise;
import entities.Set;
import entities.Workout;
import lombok.Getter;
import lombok.Setter;
import persistence.ExerciseDAO;
import persistence.SetDAO;
import persistence.WorkoutDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Model
public class EditWorkout {
	@Inject
	SetDAO setDAO;

	@Inject
	ExerciseDAO exerciseDAO;

	@Inject
	WorkoutDAO workoutDAO;


	@Getter
	private Workout workout;

	@Getter
	private Set setToCreate = new Set();

	@Getter
	private List<Exercise> allExercises;

	@Getter
	@Setter
	private Integer selectedExerciseId;


	@PostConstruct
	public void init() {
		Map<String, String> requestParameters =
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Integer workoutId = Integer.parseInt(requestParameters.get("id"));
		workout = workoutDAO.findOne(workoutId);
		loadEntities();
	}

	private void loadEntities() {
		allExercises = exerciseDAO.findAll();
	}

	@Transactional
	public void createSet() {
		init();
		Exercise exercise = allExercises
				.stream()
				.filter(x -> x.getId() == selectedExerciseId)
				.findFirst()
				.orElse(null);

		if(exercise == null)
			return;

		setToCreate.setExercise(exercise);
		setToCreate.setWorkout(workout);
		exercise.getSets().add(setToCreate);
		workout.getSets().add(setToCreate);
		setDAO.persist(setToCreate);

		setToCreate = new Set();
	}
}
