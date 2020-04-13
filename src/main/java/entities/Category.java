package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Setter
@Getter
@NamedQueries({
        @NamedQuery(name = "Category.findAll", query = "select c from Category as c"),
})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "exercise_category",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    private List<Exercise> exercises = new LinkedList();
}
