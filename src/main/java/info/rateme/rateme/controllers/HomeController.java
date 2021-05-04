package info.rateme.rateme.controllers;

import info.rateme.rateme.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public String GetHomePage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
            return "home";
        }

    @GetMapping("/login")
    public String getLoginPage(){
        return "Login";
    }
}
