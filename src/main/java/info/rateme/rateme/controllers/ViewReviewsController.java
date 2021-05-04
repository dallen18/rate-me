package info.rateme.rateme.controllers;

import info.rateme.rateme.data.ReviewRepository;
import info.rateme.rateme.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/display-reviews")
public class ViewReviewsController {

    private ReviewRepository reviewRepo;

    @Autowired
    public ViewReviewsController(ReviewRepository reviewRepo){
        this.reviewRepo = reviewRepo;
    }

    @GetMapping
    public String showReviews(Model model){
        List<Review> reviews = (List<Review>) this.reviewRepo.findAll();
        model.addAttribute("reviews", reviews);
        return "display-reviews";
    }
}
