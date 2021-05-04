package info.rateme.rateme.controllers;

import info.rateme.rateme.data.UserRepository;
import info.rateme.rateme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showUserInfo(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping
    public String handleUserForm(@ModelAttribute("user") User user, Errors errors, Model model) {
        if(errors.hasErrors())
            return "add-user";

        User userWithSameEmail = userRepo.findByEmail(user.getEmail());
        if(userWithSameEmail != null) {
            model.addAttribute("errorMsg", "Email already in use");
            return "add-user";
        } else {
            User userWithSameUsername = userRepo.findByUsername(user.getUsername());
            if(userWithSameUsername != null) {
                model.addAttribute("errorMsg", "Username already in use");
                return "add-user";
            } else {
                try {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setRoles(Set.of(User.Role.ROLE_USER));
                    user.setEnabled(true);
                    user.setCredentialsNonExpired(true);
                    user.setAccountNonLocked(true);
                    this.userRepo.save(user);
                } catch (DataIntegrityViolationException e) {
                    errors.rejectValue("email", "invalidEmail", "Email not available. Please enter another email address");
                    return "add-user";
                }
            }
        }
        return "login";
    }
}
