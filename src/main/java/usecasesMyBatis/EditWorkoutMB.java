package usecasesMyBatis;

import mybatis.model.Exercise;
import mybatis.model.Set;
import mybatis.model.Workout;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Model
public class EditWorkoutMB {
	@Inject
	mybatis.dao.SetMapper setMapper;

	@Inject
	mybatis.dao.ExerciseMapper exerciseMapper;

	@Inject
	mybatis.dao.WorkoutMapper workoutMapper;

	@Getter
	private Workout workout;

	@Getter
	private Set setToCreate;

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
		workout = workoutMapper.selectByPrimaryKey(workoutId);
		setToCreate = new Set();
		loadEntities();
	}

	private void loadEntities() {
		allExercises = exerciseMapper.selectAll();
	}

	public String getExerciseName(Integer id) {
		Exercise exercise = allExercises
				.stream()
				.filter(x -> x.getId() == id)
				.findFirst()
				.orElse(null);
		if(exercise == null)
			return "Not found";

		return exercise.getName();
	}

	@Transactional
	public void createSet() {
		Exercise exercise = allExercises
				.stream()
				.filter(x -> x.getId() == selectedExerciseId)
				.findFirst()
				.orElse(null);

		if(exercise == null)
			return;

		setToCreate.setWorkoutId(workout.getId());
		setToCreate.setExerciseId(exercise.getId());
		setMapper.insert(setToCreate);
		workout.getSets().add(setToCreate);

		setToCreate = new Set();
	}
}
