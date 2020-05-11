package usecases;

import entities.Category;
import entities.Exercise;
import lombok.Setter;
import lombok.var;
import persistence.CategoryDAO;
import persistence.ExerciseDAO;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Model
@ViewScoped
public class EditExercise implements Serializable {
	@Inject
	private ExerciseDAO exerciseDAO;

	@Inject
	private CategoryDAO categoryDAO;

	@Getter
	private Exercise exercise;

	@Setter
	@Getter
	private Integer selectedCategoryId;

	@Getter
	private String errorMessage;

	@PostConstruct
	public void init() {
		errorMessage = "";
		Map<String, String> requestParameters =
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Integer exerciseId = Integer.parseInt(requestParameters.get("id"));
		exercise = exerciseDAO.findOne(exerciseId);
	}

	public List<Category> getNotAddedCategories() {
 		List<Category> categories = categoryDAO.loadAll();
		//categories.removeAll(exercise.getCategories());
		categories.removeIf(x -> exercise.getCategories().stream().filter(e -> e.getId() == x.getId()).findFirst().isPresent());
		return categories;
	}

	@Transactional
	public void updateExercise() {
		System.out.println("updating exercise");
		try {
			exerciseDAO.update(exercise);
			init();
		}
		catch (OptimisticLockException ole)
		{
			init();
			errorMessage = "Exercise entity was not up to date";
		}
	}

	@Transactional
	public void removeCategory(Integer categoryId) {
		System.out.println("removing category:" + categoryId);
		try {
			Category category = exercise.getCategories()
					.stream()
					.filter(x -> x.getId() == categoryId)
					.findFirst()
					.orElse(null);
			if (category == null)
				return;
			exercise.getCategories().remove(category);
			category.getExercises().remove(exercise);
			categoryDAO.update(category);
			exerciseDAO.update(exercise);
			init();
		}
		catch (OptimisticLockException ole)
		{
			init();
			errorMessage = "Exercise entity was not up to date";
		}
	}

	@Transactional
	public void addCategory() {
		System.out.println("adding category");
		try {
			Category category = getNotAddedCategories()
					.stream()
					.filter(x -> x.getId() == selectedCategoryId)
					.findFirst()
					.orElse(null);
			if (category == null)
				return;
			exercise.getCategories().add(category);
			category.getExercises().add(exercise);
			exerciseDAO.update(exercise);
			categoryDAO.update(category);
			init();
		}
		catch (OptimisticLockException ole)
		{
			init();
			errorMessage = "Exercise entity was not up to date";
		}
	}
}
