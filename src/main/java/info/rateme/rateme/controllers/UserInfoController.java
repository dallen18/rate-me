package info.rateme.rateme.controllers;

import info.rateme.rateme.models.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserInfoController {

        @GetMapping
        public String showUserInfo(Model model) {
            model.addAttribute("user", new UserInfo());
            return "add-user";
        }

        @PostMapping
        public String handleStudentForm(@ModelAttribute("user") UserInfo user, RedirectAttributes attributes) {
            attributes.addAttribute("allInfo", user.getUserFirstName() + " " + user.getUserLastName() + " " + user.getUserName() + user.getEmail() + " " + user.getPassWord());
            System.out.println("First Name: " + user.getUserFirstName());
            System.out.println("Last Name: " + user.getUserLastName());
            System.out.println("User Name: " + user.getUserName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Password: " + user.getPassWord());
            return "redirect:/view";
        }
}
