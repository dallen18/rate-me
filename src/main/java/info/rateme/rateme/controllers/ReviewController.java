package info.rateme.rateme.controllers;

import info.rateme.rateme.data.ReviewRepository;
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

    @Autowired
    public ReviewController(ReviewRepository reviewRepo){
        this.reviewRepo = reviewRepo;
    }

    @GetMapping
    public String sendAddReviewForm(Model model) {
        model.addAttribute("review", new Review());
        return "add-review";
    }

    @GetMapping("/view/{id}")
    public String showReview(@PathVariable Long id, Model model){
        Review review = this.reviewRepo.findById(id).get();
        model.addAttribute("review", review);
        return "view-review";
    }

    @PostMapping
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
            this.reviewRepo.save(review);
        } catch (DataIntegrityViolationException e){
            errors.rejectValue("review", "invalidReview", "Review already made");
            return "view-review";
        }

        return "redirect:/view";
    }

    private void updatedOriginalReview(Review original, Review update){
       /* original.setMovieName(update.getMovieName());
        original.setCategory(update.getCategory());
        original.setEpisodes(update.getEpisodes());*/
        original.setDescription(update.getDescription());
        original.setRating(update.getRating());
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id){
        this.reviewRepo.deleteById(id);
        return "redirect:/display-reviews";
    }

}
