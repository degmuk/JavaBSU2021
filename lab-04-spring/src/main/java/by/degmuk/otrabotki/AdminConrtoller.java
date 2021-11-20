package by.degmuk.otrabotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

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

    @GetMapping("/otrabotka/edit")
    public String addOtrabotka(HttpSession session, Model model) {
        Otrabotka otrabotka;
        if (session.getAttribute("otrabotka") == null) {
            otrabotka = new Otrabotka();
            session.setAttribute("otrabotka", otrabotka);
        } else {
            otrabotka = (Otrabotka) session.getAttribute("otrabotka");
        }
        model.addAttribute("otrabotka", otrabotka);
        return "edit_otrabotka";
    }

    @PostMapping(value = "/otrabotka/edit_text",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String editOtrabotkaText(HttpSession session, String text,
                                    Model model) {
        var otrabotka = (Otrabotka) session.getAttribute("otrabotka");
        otrabotka.setText(text);
        model.addAttribute("otrabotka", otrabotka);
        return "redirect:/admin/otrabotka/edit";
    }

    @PostMapping(value = "/otrabotka/add_slave",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addNewOtrabotkaSlave(HttpSession session, String name,
                                       Integer room, Integer studak,
                                       Model model) {
        var otrabotka = (Otrabotka) session.getAttribute("otrabotka");
        model.addAttribute("otrabotka", otrabotka);
        Student student = null;
        if (studak != null) {
            student = students.getByStudak(studak);
            if (student == null || student.room.equals(room) ||
                    student.name.equals(name)) {
                return "redirect:/admin/otrabotka/edit";
            }
        } else if (name != null && room != null) {
            student = students.getByNameAndRoom(name, room);
        }
        if (student != null && !otrabotka.students.contains(student)) {
            otrabotka.students.add(student);
        }
        return "redirect:/admin/otrabotka/edit";
    }

    @PostMapping(value = "/otrabotka/start")
    public String startOtrabotka(HttpSession session) {
        var otrabotka = (Otrabotka) session.getAttribute("otrabotka");
        otrabotki.save(otrabotka);
        session.removeAttribute("otrabotka");
        return "redirect:/";
    }

    @PostMapping(value = "/otrabotka/stop")
    public String stopOtrabotka(Integer otrabotka_id) {
        var otrabotka = otrabotki.getById(otrabotka_id);
        otrabotka.stop();
        otrabotki.save(otrabotka);
        return "redirect:/otrabotka/list";
    }
}
