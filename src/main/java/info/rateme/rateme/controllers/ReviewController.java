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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {

    private ReviewRepository reviewRepo;
    private MovieRepository movieRepo;
    private UserRepository userRepo;

    @Autowired
    public ReviewController(ReviewRepository reviewRepo, MovieRepository movieRepo, UserRepository userRepo){
        this.userRepo = userRepo;
        this.reviewRepo = reviewRepo;
        this.movieRepo = movieRepo;
    }

    @GetMapping("/add/{movie_id}{user_id}")
    public String sendAddReviewForm(@PathVariable Long movie_id, Model model) {
        model.addAttribute("review", new Review());
        Movie movie = movieRepo.findById(movie_id).get();
        model.addAttribute("m", movie);
        return "add-review";
    }

    @GetMapping("/view/{id}")
    public String showReview(@PathVariable Long id, Model model){
        Review review = this.reviewRepo.findById(id).get();
        model.addAttribute("review", review);
        return "view-review";
    }

    @PostMapping("/add")
    public String handleReviewForm(@Valid @ModelAttribute("review") Review review, Errors errors, @AuthenticationPrincipal User user) {
        if(errors.hasErrors())
            return "add-review";
        try {
            review.setUser(user);
            this.reviewRepo.save(review);
        } catch (DataIntegrityViolationException e) {
            return "add-review";
        }
        return "redirect:/display-reviews";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id){
        this.reviewRepo.deleteById(id);
        return "redirect:/display-movies";
    }

    @PostMapping("/edit/{id}")
    public String handleEditReviewForm(@PathVariable Long id, @Valid @ModelAttribute("review") Review review, Errors errors) {
        if(errors.hasErrors())
            return "view-review";

        try {
            Review originalReview = this.reviewRepo.findById(id).get();
            updatedOriginalReview(originalReview, review);
            this.reviewRepo.save(originalReview);
        } catch (DataIntegrityViolationException e){
            errors.rejectValue("review", "invalidReview", "Review already made");
            return "view-review";
        }
        return "redirect:/view";
    }

    private void updatedOriginalReview(Review original, Review update){
        original.setDescription(update.getDescription());
        original.setRating(update.getRating());
    }

}
