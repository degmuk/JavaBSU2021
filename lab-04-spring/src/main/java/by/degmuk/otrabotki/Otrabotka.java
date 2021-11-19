package by.degmuk.otrabotki;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Otrabotka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToMany
    List<Student> students = new ArrayList<>();
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = null;
    String text;

    void stop() {
        endTime = LocalDateTime.now();
        int hours = 42 + (int)ChronoUnit.HOURS.between(startTime, endTime);
        for (var student : students) {
            student.otrabotki.add(this);
            student.totalHours += hours;
        }
    }
}
