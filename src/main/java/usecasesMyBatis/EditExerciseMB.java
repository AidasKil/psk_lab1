package usecasesMyBatis;

import lombok.Getter;
import lombok.Setter;

import mybatis.model.Category;
import mybatis.model.ExerciseCategory;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
public class EditExerciseMB {
	@Inject
	private mybatis.dao.ExerciseMapper exerciseMapper;

	@Inject
	private mybatis.dao.CategoryMapper categoryMapper;

	@Inject
	private mybatis.dao.ExerciseCategoryMapper exerciseCategoryMapper;

	@Getter
	private mybatis.model.Exercise exercise;

	@Setter
	@Getter
	private Integer selectedCategoryId;

	@PostConstruct
	public void init() {
		Map<String, String> requestParameters =
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		Integer exerciseId = Integer.parseInt(requestParameters.get("id"));
		exercise = exerciseMapper.selectByPrimaryKey(exerciseId);
	}

	public List<Category> getNotAddedCategories() {
		List<Category> categories = categoryMapper.selectAll();
		if(categories == null)
			categories = new ArrayList();

		for(Category c : exercise.getCategories())
			categories.removeIf(x -> x.getId() == c.getId());
		return categories;
	}

	@Transactional
	public void updateExercise() {
		System.out.println("updating exercise");
		exerciseMapper.updateByPrimaryKey(exercise);
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

		ExerciseCategory ec = new mybatis.model.ExerciseCategory();
		ec.setCategoryId(category.getId());
		ec.setExerciseId(exercise.getId());
		exerciseCategoryMapper.delete(ec);

		exercise.getCategories().remove(category);
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

		ExerciseCategory ec = new mybatis.model.ExerciseCategory();
		ec.setCategoryId(category.getId());
		ec.setExerciseId(exercise.getId());
		exerciseCategoryMapper.insert(ec);

		exercise.getCategories().add(category);
	}
}
