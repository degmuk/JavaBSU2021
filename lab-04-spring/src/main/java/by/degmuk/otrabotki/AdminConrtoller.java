package by.degmuk.otrabotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
public class AdminConrtoller {
    @Autowired
    StudentRepository students;

    @Autowired
    OtrabotkiRepository otrabotki;

    @GetMapping("/student/add")
    public String addStudent() {
        return "add_student";
    }

    @PostMapping(value = "/student/add",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addStudent(Student student) {
        student.totalHours = 0;
        students.save(student);
        return "redirect:/student/list";
    }

    @GetMapping("/otrabotka/new")
    public String addOtrabotka(Model model) {
        var otrabotka = new Otrabotka();
        otrabotki.save(otrabotka);
        return editOtrabotka(model, otrabotka);
    }

    public String editOtrabotka(Model model, Otrabotka otrabotka) {
        model.addAttribute("otrabotka", otrabotka);
        return "edit_otrabotka";
    }

    @GetMapping("/otrabotka/{id}/edit")
    public String getEditOtrabotka(Model model, @PathVariable Integer id) {
        var otrabotka = otrabotki.getById(id);
        return editOtrabotka(model, otrabotka);
    }

    @PostMapping(value = "/otrabotka/{id}/edit",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postEditOtrabotka(Model model, @PathVariable Integer id,
                                    String text, Student new_slave) {
        var otrabotka = otrabotki.getById(id);
        if (otrabotka == null) {
            return editOtrabotka(model, otrabotka);
        }
        if (text != null && !text.isEmpty()) {
            otrabotka.setText(text);
        }
        if (new_slave != null) {
            Student student = null;
            Integer studak = new_slave.getStudak();
            String name= new_slave.getName();
            Integer room = new_slave.getRoom();
            if (studak != null) {
                student = students.getByStudak(studak);
                if (student == null ||
                        (room != null && !student.room.equals(room)) ||
                        (name != null && !name.isEmpty() &&
                                !student.name.equals(name))) {
                    student = null;
                }
            } else if (name != null && room != null) {
                student = students.getByNameAndRoom(name, room);
            }
            if (student != null) {
                otrabotka.students.add(student);
                student.otrabotki.add(otrabotka);
            }
        }
        otrabotki.save(otrabotka);
        return editOtrabotka(model, otrabotka);
    }

    @PostMapping(value = "/otrabotka/{id}/start")
    public String startOtrabotka(@PathVariable Integer id) {
        var otrabotka = otrabotki.getById(id);
        otrabotka.setStartTime(LocalDateTime.now());
        otrabotki.save(otrabotka);
        return "redirect:/otrabotka/list";
    }

    @PostMapping(value = "/otrabotka/{id}/stop")
    public String stopOtrabotka(@PathVariable Integer id) {
        var otrabotka = otrabotki.getById(id);
        otrabotka.stop();
        otrabotki.save(otrabotka);
        return "redirect:/otrabotka/list";
    }
}
