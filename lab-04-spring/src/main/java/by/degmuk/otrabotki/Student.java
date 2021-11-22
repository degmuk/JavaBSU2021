package by.degmuk.otrabotki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Integer studak;
    private Integer course;
    private Integer room;
    private String name;
    private Integer totalHours;
    @ManyToMany
    @JoinTable(name="slavery",
            joinColumns=@JoinColumn(name="studak"),
            inverseJoinColumns=@JoinColumn(name="id"))
    @JsonIgnoreProperties("students")
    Set<Otrabotka> otrabotki = new HashSet<>();

    void addOtrabotka(Otrabotka otrabotka) {
        otrabotki.add(otrabotka);
    }
}
