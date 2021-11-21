package by.degmuk.otrabotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
    StudentRepository students;

    @Autowired
    OtrabotkiRepository otrabotki;

    @GetMapping("/")
    public String startPage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/student/list")
    public String getStudentsList(Model model, String sorting, String name) {
        if (name == null) {
            name = "";
        }
        if ("ascending".equals(sorting)) {
            model.addAttribute("students",
                    students.getAllByNameContainsOrderByTotalHoursAsc(name));
        } else if ("descending".equals(sorting)) {
            model.addAttribute("students",
                    students.getAllByNameContainsOrderByTotalHoursDesc(name));
        } else {
            model.addAttribute("students",
                    students.getAllByNameContainsOrderByName(name));
        }
        return "student_list";
    }

    @GetMapping("/student")
    public String getStudent(Model model, Integer studak) {
        Student student = students.getByStudak(studak);
        model.addAttribute("student", student);
        return "student_info";
    }

    @GetMapping("/otrabotka/list")
    public String getOtrabotki(Model model) {
        model.addAttribute("otrabotki", otrabotki.getAllByOrderByStartTime());
        return "otrabotki_list";
    }
}
