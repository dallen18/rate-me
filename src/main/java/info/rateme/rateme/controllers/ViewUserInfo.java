package info.rateme.rateme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewUserInfo {

        @GetMapping
        public String showStudentName(@ModelAttribute("allInfo") Object flashAttribute, Model model){
            model.addAttribute("allInfo", flashAttribute);
            return "display-user";
        }
}
