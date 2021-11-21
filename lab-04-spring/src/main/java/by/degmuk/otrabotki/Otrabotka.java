package by.degmuk.otrabotki;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Otrabotka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Integer id;
    @ManyToMany
    @JoinTable(name = "slavery", joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "studak"))
    Set<Student> students = new HashSet<>();
    LocalDateTime startTime = null;
    LocalDateTime endTime = null;
    String text;

    void stop() {
        endTime = LocalDateTime.now();
        int hours = 42 + (int) ChronoUnit.HOURS.between(startTime, endTime);
        for (var student : students) {
            student.otrabotki.add(this);
            student.totalHours += hours;
        }
    }
}
