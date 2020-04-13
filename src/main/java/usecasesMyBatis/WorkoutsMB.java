package usecasesMyBatis;

import mybatis.model.Workout;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Model
public class WorkoutsMB {
	@Inject
	private mybatis.dao.WorkoutMapper workoutMapper;

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
		allWorkouts = workoutMapper.selectAll();
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
		workoutMapper.insert(workout);
		loadWorkouts();
	}
}
