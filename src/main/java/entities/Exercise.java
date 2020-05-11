package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Exercise.findAllUncategorized", query = "select e from Exercise as e " +
                "left join e.categories as c " +
                "where c is null"),
        @NamedQuery(name = "Exercise.findAll", query = "select e from Exercise as e"),
})
@Table(name="EXERCISE")
public class Exercise implements Serializable {
    @Version
    private Integer version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy="exercises", fetch = FetchType.EAGER)
    private List<Category> categories= new LinkedList();

    @OneToMany(mappedBy="exercise")
    private List<Set> sets;
}
