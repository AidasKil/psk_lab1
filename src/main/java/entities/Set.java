package entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NamedQuery(name = "Set.findAll", query = "select c from Set as c")
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Workout workout;

    @ManyToOne
    private Exercise exercise;

    private Double weight;

    private Integer repetitions;
}
