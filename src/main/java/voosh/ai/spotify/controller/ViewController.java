package voosh.ai.spotify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login.html")
    public String loginPage() {
        return "login";  // This maps to src/main/resources/templates/login.html
    }
}
