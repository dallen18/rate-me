package info.rateme.rateme.controllers;

import info.rateme.rateme.data.MovieRepository;
import info.rateme.rateme.data.ReviewRepository;
import info.rateme.rateme.data.UserRepository;
import info.rateme.rateme.models.Movie;
import info.rateme.rateme.models.Review;
import info.rateme.rateme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepo;
    private ReviewRepository reviewRepo;
    private MovieRepository movieRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepo, ReviewRepository reviewRepo, MovieRepository movieRepo, PasswordEncoder passwordEncoder){
        this.movieRepo = movieRepo;
        this.userRepo = userRepo;
        this.reviewRepo = reviewRepo;
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

    /*@PostMapping("/edit-user/{id}")
    public String handleEditUserForm(@PathVariable Long id, @Valid @ModelAttribute("user") User user, Errors errors, Model model) {
        if(errors.hasErrors())
            return "edit-user";

        List<User> users = (List<User>) userRepo.findByEmail(user.getEmail());
        boolean matchfound = users.stream().anyMatch(u -> users.equals(user.getEmail()));
        if(matchfound) {
            model.addAttribute("errorMsg", "Email already in use");
            return "edit-user";
        }

        List<User> username = (List<User>) userRepo.findByUsername(user.getUsername());
        boolean matchfounds = username.stream().anyMatch(u -> username.equals(user.getUsername()));
        if(matchfounds) {
            model.addAttribute("errorMsg", "Username already in use");
            return "edit-user";
        }

        try {
            User originalUser = this.userRepo.findById(id).get();
            updatedOriginalUser(originalUser, user);
            this.userRepo.save(user);
        } catch (DataIntegrityViolationException e){
            errors.rejectValue("user", "invalidUser", "Invalid Email");
            return "edit-user";
        }

        this.userRepo.save(user);
        return "redirect:/login";

    }*/

    private void updatedOriginalUser(User original, User update) {
        original.setUsername(update.getUsername());
        original.setPassword(update.getPassword());

    }
}
