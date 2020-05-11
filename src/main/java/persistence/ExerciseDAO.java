package persistence;

import entities.Exercise;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.List;

@ApplicationScoped
public class ExerciseDAO {
    @Inject
    private EntityManager entityManager;

    public void persist(Exercise exercise) {
        entityManager.persist(exercise);
    }

    public void update(Exercise exercise) {
        entityManager.merge(exercise);
        entityManager.flush();
    }

    public List<Exercise> findAllUncategorized() {
        return entityManager.createNamedQuery("Exercise.findAllUncategorized", Exercise.class)
                .getResultList();
    }

    public List<Exercise> findAll() {
        return entityManager.createNamedQuery("Exercise.findAll", Exercise.class).getResultList();
    }

    public Exercise findOne(Integer id) {
        return entityManager.find(Exercise.class, id);
    }

    public void delete(Exercise exercise) {
        entityManager.remove(exercise);
    }
}
