package by.degmuk.otrabotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OtrabotkiService {
    @Autowired
    StudentRepository students;

    @Autowired
    OtrabotkiRepository otrabotki;

    void addStudent(Student student) {
        student.setTotalHours(0);
        students.save(student);
    }

    Integer createNewOtrabotka() {
        var otrabotka = new Otrabotka();
        otrabotki.save(otrabotka);
        return otrabotka.getId();
    }

    Otrabotka getOtrabotka(Integer id) {
        return otrabotki.getById(id);
    }

    void setOtrabotkaText(Integer id, String text) {
        var otrabotka = otrabotki.getById(id);
        if (otrabotka == null || text == null || text.isEmpty()) {
            return;
        }
        otrabotka.setText(text);
        otrabotki.save(otrabotka);
    }

    void addOtrabotkaSlave(Integer id, Student new_slave) {
        var otrabotka = otrabotki.getById(id);
        if (otrabotka == null || new_slave == null) {
            return;
        }
        Student student = null;
        Integer studak = new_slave.getStudak();
        String name = new_slave.getName();
        Integer room = new_slave.getRoom();
        if (studak != null) {
            student = students.getByStudak(studak);
            if (student == null ||
                    (room != null && !student.getRoom().equals(room)) ||
                    (name != null && !name.isEmpty() &&
                            !student.getName().equals(name))) {
                student = null;
            }
        } else if (name != null && room != null) {
            student = students.getByNameAndRoom(name, room);
        }
        if (student != null) {
            student.addOtrabotka(otrabotka);
        }
        otrabotki.save(otrabotka);
    }

    void startOtrabotka(Integer id) {
        var otrabotka = otrabotki.getById(id);
        if (otrabotka == null) {
            return;
        }
        otrabotka.start();
        otrabotki.save(otrabotka);
    }

    void stopOtrabotka(Integer id) {
        var otrabotka = otrabotki.getById(id);
        if (otrabotka == null) {
            return;
        }
        otrabotka.stop();
        otrabotki.save(otrabotka);
    }

    void setOtrabotkaTime(Integer id, LocalDateTime startTime, Integer hours) {
        var otrabotka = otrabotki.getById(id);
        if (otrabotka == null) {
            return;
        }
        if (hours != null) {
            Integer oldHours = otrabotka.getTotalHours();
            if (oldHours != null) {
                for (var student : otrabotka.getStudents()) {
                    student.setTotalHours(student.getTotalHours() - oldHours);
                }
            }
            for (var student : otrabotka.getStudents()) {
                student.setTotalHours(student.getTotalHours() + hours);
            }
            otrabotka.setTotalHours(hours);
        }
        if (startTime != null) {
            otrabotka.setStartTime(startTime);
        }
        otrabotki.save(otrabotka);
    }
}
