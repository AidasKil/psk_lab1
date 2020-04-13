package usecasesMyBatis;

import mybatis.model.Category;
import mybatis.model.Exercise;
import mybatis.model.ExerciseCategory;
import lombok.Getter;
import lombok.Setter;
import mybatis.dao.CategoryMapper;
import mybatis.dao.ExerciseMapper;
import mybatis.dao.ExerciseCategoryMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class CategoriesMB {
    @Inject
    private CategoryMapper categoryMapper;

    @Inject
    private ExerciseMapper exerciseMapper;

    @Inject
    private ExerciseCategoryMapper exerciseCategoryMapper;

    @Getter
    private List<Category> allCategories;

    @Getter
    private List<Exercise> uncategorizedExercises;

    @Getter
    private Category categoryToCreate;

    @Getter
    private Exercise exerciseToCreate;

    @Setter
    @Getter
    private Integer selectedCategoryId;

    private void loadEntities() {
        allCategories = categoryMapper.selectAll();
        uncategorizedExercises = exerciseMapper.selectAllUncategorized();
    }

    @PostConstruct
    public void init(){
        loadEntities();
        categoryToCreate = new Category();
        exerciseToCreate = new Exercise();
    }

    @Transactional
    public void createCategory()
    {
        System.out.println("creating category MB");
        categoryMapper.insert(categoryToCreate);
        categoryToCreate = new Category();
        loadEntities();
    }

    @Transactional
    public void createExercise()
    {
        System.out.println("creating exercise");
        Category category = allCategories
                .stream()
                .filter(x -> x.getId() == selectedCategoryId)
                .findFirst()
                .orElse(null);
        if(category == null)
            return; // wip why is this called?

        exerciseMapper.insert(exerciseToCreate);

        // Insert relationship
        ExerciseCategory ec = new ExerciseCategory();
        ec.setCategoryId(category.getId());
        ec.setExerciseId(exerciseToCreate.getId());
        exerciseCategoryMapper.insert(ec);

        exerciseToCreate = new Exercise();
        loadEntities();
    }

}
