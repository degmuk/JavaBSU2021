package by.degmuk.otrabotki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Integer id;
    @ManyToMany
    @JsonIgnoreProperties("otrabotki")
    @JoinTable(name = "slavery", joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "studak"))
    private Set<Student> students = new HashSet<>();
    private LocalDateTime startTime = null;
    private Integer totalHours = null;
    private String text;

    public void start() {
        startTime = LocalDateTime.now();
    }

    public void stop() {
        var endTime = LocalDateTime.now();
        totalHours = (int) ChronoUnit.SECONDS.between(startTime, endTime);
        for (var student : students) {
            student.getOtrabotki().add(this);
            student.setTotalHours(student.getTotalHours() + totalHours);
        }
    }
}
