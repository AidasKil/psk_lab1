package persistence;

import entities.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class SetDAO {
	@Inject
	private EntityManager entityManager;

	public void persist(Set set) {
		entityManager.persist(set);
	}

	public List<Set> findAll() {
		return entityManager.createNamedQuery("Set.findAll", Set.class).getResultList();
	}
}
