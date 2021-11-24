package by.degmuk.otrabotki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RestApiController {
    @Autowired
    private StudentRepository students;

    @Autowired
    private OtrabotkiRepository otrabotki;

    @GetMapping("/students/list")
    public Iterable<Student> getStudents() {
        return students.findAllByOrderByName();
    }

    @GetMapping("/otrabotka/list")
    public Iterable<Otrabotka> getOtrabotki() {
        return otrabotki.findAllByOrderByStartTimeDesc();
    }
}
