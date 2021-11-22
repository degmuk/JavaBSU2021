package by.degmuk.otrabotki;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginContoller {
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
}
