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
    public String getStudents(Model model) {
        model.addAttribute("students", students.getAllByOrderByName());
        return "student_list";
    }

    @GetMapping("/otrabotka/list")
    public String getOtrabotki(Model model) {
        model.addAttribute("otrabotki", otrabotki.getAllByOrderByStartTime());
        return "otrabotki_list";
    }
}
