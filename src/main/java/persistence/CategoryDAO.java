package persistence;

import entities.Category;

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

    public void persist(Category category) {
        entityManager.persist(category);
    }
}
