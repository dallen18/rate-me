package info.rateme.rateme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public String GetHomePage() {
            return "index";
        }

    @GetMapping("/login")
    public String getLoginPage(){
        return "Login";
    }
}
