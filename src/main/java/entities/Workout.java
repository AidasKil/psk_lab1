package entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Workout.findAll", query = "select w from Workout as w"),
})
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // wip unique
    private Date date;

    @OneToMany(mappedBy = "workout")
    private List<Set> sets;
}
