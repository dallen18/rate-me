package info.rateme.rateme.controllers;

import info.rateme.rateme.data.UserRepository;
import info.rateme.rateme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserInfoController {

    private UserRepository userRepo;

    @Autowired
    public UserInfoController(UserRepository userRepo){
        this.userRepo = userRepo;
    }

        @GetMapping
        public String showUserInfo(Model model) {
            model.addAttribute("user", new User());
            return "add-user";
        }

        @PostMapping
        public String handleStudentForm(@Valid @ModelAttribute("user") User user, Errors errors) {
          if(errors.hasErrors())
              return "add-user";
          this.userRepo.save(user);
          return "redirect:/users-view";
        }
}
