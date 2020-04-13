package usecases;

import entities.Category;
import entities.Exercise;
import lombok.Getter;
import lombok.Setter;
import persistence.CategoryDAO;
import persistence.ExerciseDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Categories {
    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private ExerciseDAO exerciseDAO;

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
        allCategories = categoryDAO.loadAll();
        uncategorizedExercises = exerciseDAO.findAllUncategorized();
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
        System.out.println("creating category");
        categoryDAO.persist(categoryToCreate);
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

        exerciseToCreate.getCategories().add(category);
        category.getExercises().add(exerciseToCreate);
        exerciseDAO.persist(exerciseToCreate);
        exerciseToCreate = new Exercise();
        loadEntities();
    }

}
