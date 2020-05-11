package persistence;

import entities.Category;
import entities.Exercise;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class CategoryDAO {
    @Inject
    private EntityManager entityManager;

    public List<Category> loadAll() {
        return entityManager.createNamedQuery("Category.findAll", Category.class).getResultList();
    }
    public Category findOne(Integer id) {
        return entityManager.find(Category.class, id);
    }

    public void refresh(Category category) {
        entityManager.refresh(category);
    }

    public void persist(Category category) {
        entityManager.persist(category);
    }
    public void update(Category category) {
        entityManager.merge(category);
        entityManager.flush();
    }
}
