package info.rateme.rateme.controllers;

import info.rateme.rateme.data.UserRepository;
import info.rateme.rateme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UpdateUserController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UpdateUserController(UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/view")
    public String GetUserPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
            return "view-user";
    }

    @GetMapping("/edit")
    public String EditUserPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "edit-user";
    }


    @PostMapping("/edit")
    public String handleEditUserForm(@ModelAttribute("user") User user, @AuthenticationPrincipal User org, Errors errors, Model model) {
        if(errors.hasErrors()){
            return "edit-user";
        }
        User userWithSameEmail = userRepo.findByEmail(user.getEmail());
        if(userWithSameEmail != null) {
            model.addAttribute("errorMsg", "Email already in use");
            return "edit-user";
        } else {
            User userWithSameUsername = userRepo.findByUsername(user.getUsername());
            if(userWithSameUsername != null) {
                model.addAttribute("errorMsg", "Username already in use");
                return "edit-user";
            } else {
                try {
                    System.out.println(org.getUserFirstname());
                    System.out.println(org.getUserLastname());
                    System.out.println(org.getId());
                    System.out.println(org.getAuthorities());
                    updatedOriginalUser(org, user);
                    this.userRepo.save(org);
                } catch (DataIntegrityViolationException e){
                    errors.rejectValue("user", "invalidUser", "Invalid Email or Username");
                    return "edit-user";
                }
                return "redirect:/display-movies";
            }
        }

    }

    private void updatedOriginalUser(User original, User update) {
        original.setUsername(update.getUsername());
        original.setPassword(passwordEncoder.encode(update.getPassword()));
        original.setEmail(update.getEmail());
    }
}
