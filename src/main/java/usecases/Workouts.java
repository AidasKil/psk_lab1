package usecases;

import entities.Workout;
import lombok.Getter;
import lombok.Setter;
import persistence.WorkoutDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Date;

@Model
public class Workouts {
	@Inject
	private WorkoutDAO workoutDAO;

	@Getter
	private List<Workout> allWorkouts;

	@Getter
	@Setter
	private Date workoutDate;

	@PostConstruct
	public void init() {
		loadWorkouts();
	}

	private void loadWorkouts() {
		allWorkouts = workoutDAO.findAll();
	}

	private java.sql.Date dateToSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	@Transactional
	public void createWorkout() {
		if(workoutDate == null)
			return;
		Workout workout = new Workout();
		workout.setDate(dateToSqlDate(workoutDate));
		workoutDAO.persist(workout);
		loadWorkouts();
	}
}
