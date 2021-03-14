package info.rateme.rateme.controllers;

import info.rateme.rateme.data.UserRepository;
import info.rateme.rateme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users-view")
public class ViewUsersInfoController {

    private UserRepository userRepo;

    @Autowired
    public ViewUsersInfoController(UserRepository userRepo){
        this.userRepo = userRepo;
    }

        @GetMapping
        public String showStudentName(Model model){
        List<User> users = (List<User>) this.userRepo.findAll();
        model.addAttribute("users", users);
        return "display-users";
        }
}
