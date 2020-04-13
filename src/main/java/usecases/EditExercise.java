package usecases;

import entities.Category;
import entities.Exercise;
import lombok.Setter;
import lombok.var;
import persistence.CategoryDAO;
import persistence.ExerciseDAO;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Model
public class EditExercise {
	@Inject
	private ExerciseDAO exerciseDAO;

	@Inject
	private CategoryDAO categoryDAO;

	@Getter
	private Exercise exercise;

	@Setter
	@Getter// wip needs the setter?
	private Integer selectedCategoryId;

	@PostConstruct
	public void init() {
		Map<String, String> requestParameters =
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Integer exerciseId = Integer.parseInt(requestParameters.get("id"));
		exercise = exerciseDAO.findOne(exerciseId);
	}

	public List<Category> getNotAddedCategories() {
		List<Category> categories = categoryDAO.loadAll();
		categories.removeAll(exercise.getCategories());
		return categories;
	}

	@Transactional
	public void updateExercise() {
		System.out.println("updating exercise");
		exerciseDAO.persist(exercise);
	}

	@Transactional
	public void deleteExercise() {
		exerciseDAO.delete(exercise);// Wip do i need this??
	}

	@Transactional
	public void removeCategory(Integer categoryId) {
		System.out.println("removing category:" + categoryId);
		Category category = exercise.getCategories()
				.stream()
				.filter(x -> x.getId() == categoryId)
				.findFirst()
				.orElse(null);
		if(category == null)
			return;
		exercise.getCategories().remove(category);
		category.getExercises().remove(exercise);
	}

	@Transactional
	public void addCategory() {
		System.out.println("adding category");
		Category category = getNotAddedCategories()
				.stream()
				.filter(x -> x.getId() == selectedCategoryId)
				.findFirst()
				.orElse(null);
		if(category == null)
			return;
		exercise.getCategories().add(category);
		category.getExercises().add(exercise);
	}
}
