package persistence;


import entities.Workout;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class WorkoutDAO {
	@Inject
	private EntityManager entityManager;

	public void persist(Workout workout) {
		entityManager.persist(workout);
	}

	public Workout findOne(Integer id) {
		return entityManager.find(Workout.class, id);
	}

	public List<Workout> findAll() {
		return entityManager.createNamedQuery("Workout.findAll", Workout.class).getResultList();
	}
}
