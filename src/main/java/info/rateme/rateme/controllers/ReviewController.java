package info.rateme.rateme.controllers;

import info.rateme.rateme.data.MovieRepository;
import info.rateme.rateme.data.ReviewRepository;
import info.rateme.rateme.models.Movie;
import info.rateme.rateme.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/review")
public class ReviewController {

    private ReviewRepository reviewRepo;
    private MovieRepository movieRepo;

    @Autowired
    public ReviewController(ReviewRepository reviewRepo, MovieRepository movieRepo){
        this.reviewRepo = reviewRepo;
        this.movieRepo = movieRepo;
    }

    @GetMapping("/add/{movie_id}")
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
    public String handleReviewForm(@Valid @ModelAttribute("review") Review review, Errors errors) {
      if(errors.hasErrors())
          return "add-review";
      this.reviewRepo.save(review);
      return "redirect:/display-reviews";
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
