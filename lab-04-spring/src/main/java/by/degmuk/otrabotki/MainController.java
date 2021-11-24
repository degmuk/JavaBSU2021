package by.degmuk.otrabotki;

import by.degmuk.otrabotki.model.Student;
import by.degmuk.otrabotki.repositories.OtrabotkiRepository;
import by.degmuk.otrabotki.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private StudentRepository students;

    @Autowired
    private OtrabotkiRepository otrabotki;

    @GetMapping("/")
    public String startPage() {
        return "index";
    }

    @GetMapping("/student/list")
    public String getStudentsList(Model model, String sorting, String name) {
        if (name == null) {
            name = "";
        }
        if ("ascending".equals(sorting)) {
            model.addAttribute("students",
                    students.findAllByNameContainsOrderByTotalHoursAsc(name));
        } else if ("descending".equals(sorting)) {
            model.addAttribute("students",
                    students.findAllByNameContainsOrderByTotalHoursDesc(name));
        } else {
            model.addAttribute("students",
                    students.findAllByNameContainsOrderByName(name));
        }
        return "student_list";
    }

    @GetMapping("/student")
    public String getStudent(Model model, Integer studak) {
        Student student = students.findByStudak(studak);
        model.addAttribute("student", student);
        return "student_info";
    }

    @GetMapping("/otrabotka/list")
    public String getOtrabotki(Model model) {
        model.addAttribute("otrabotki", otrabotki.findAllByOrderByStartTimeDesc());
        return "otrabotki_list";
    }
}
