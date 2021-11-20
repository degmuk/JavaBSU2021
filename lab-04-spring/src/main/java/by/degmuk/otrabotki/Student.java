package by.degmuk.otrabotki;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Student {
    @Id
    @EqualsAndHashCode.Include
    Integer studak;
    Integer course;
    Integer room;
    String name;
    Integer totalHours;
    @ManyToMany
    @JoinTable(name="slavery",
            joinColumns=@JoinColumn(name="studak"),
            inverseJoinColumns=@JoinColumn(name="id"))
    Set<Otrabotka> otrabotki = new HashSet<>();
}
