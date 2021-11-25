package by.degmuk.otrabotki;

import by.degmuk.otrabotki.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/admin")
public class AdminConrtoller {
    @Autowired
    private OtrabotkiService otrabotkiService;

    @GetMapping("/student/add")
    public String addStudent() {
        return "add_student";
    }

    @PostMapping(value = "/student/add",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addStudent(Student student) {
        if (student != null && student.getName() != null &&
                !student.getName().isEmpty() && student.getCourse() != null &&
                student.getStudak() != null) {
            otrabotkiService.addStudent(student);
        }
        return "redirect:/student/list";
    }

    @GetMapping("/otrabotka/new")
    public String addOtrabotka() {
        Integer id = otrabotkiService.createNewOtrabotka();
        return "redirect:/admin/otrabotka/" + id + "/edit";
    }

    @GetMapping("/otrabotka/{id}/edit")
    public String getEditOtrabotka(Model model, @PathVariable Integer id) {
        var otrabotka = otrabotkiService.getOtrabotka(id);
        model.addAttribute("otrabotka", otrabotka);
        return "edit_otrabotka";
    }

    @PostMapping(value = "/otrabotka/{id}/edit",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String postEditOtrabotka(Model model, @PathVariable Integer id,
                                    String text, Student new_slave,
                                    @DateTimeFormat(
                                            iso = DateTimeFormat.ISO.DATE)
                                            LocalDate startDate,
                                    @DateTimeFormat(
                                            iso = DateTimeFormat.ISO.TIME)
                                            LocalTime startTime,
                                    Integer hours) {
        otrabotkiService.setOtrabotkaText(id, text);
        otrabotkiService.addOtrabotkaSlave(id, new_slave);
        var startDateTime = startDate != null && startTime != null ?
                LocalDateTime.of(startDate, startTime) : null;
        otrabotkiService.setOtrabotkaTime(id, startDateTime, hours);
        return "redirect:/admin/otrabotka/" + id + "/edit";
    }

    @PostMapping(value = "/otrabotka/{id}/start")
    public String startOtrabotka(@PathVariable Integer id) {
        otrabotkiService.startOtrabotka(id);
        return "redirect:/otrabotka/list";
    }

    @PostMapping(value = "/otrabotka/{id}/stop")
    public String stopOtrabotka(@PathVariable Integer id) {
        otrabotkiService.stopOtrabotka(id);
        return "redirect:/otrabotka/list";
    }
}
