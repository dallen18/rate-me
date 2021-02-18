package info.rateme.rateme;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

public class HomeController {
    @Controller
    @RequestMapping("/")
    public class HelloWorldControllers {

        @GetMapping("/")
        public String GetHomePage() {
            return "index";
        }
    }
}
