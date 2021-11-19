package by.degmuk.otrabotki;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    List<Otrabotka> otrabotki;
}
