package info.rateme.rateme.controllers;

import info.rateme.rateme.data.MovieRepository;
import info.rateme.rateme.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private MovieRepository movieRepo;

    @Autowired
    public MovieController(MovieRepository movieRepo){
        this.movieRepo = movieRepo;
    }

    @GetMapping
    public String showMovieInfo(Model model) {
        model.addAttribute("movie", new Movie());
        return "add-movie";
    }

    @GetMapping("/view/{id}")
    public String showReview(@PathVariable Long id, Model model){
        Movie movie = this.movieRepo.findById(id).get();
        model.addAttribute("movie", movie);
        return "view-reviews";
    }

    @PostMapping
    public String handleMovieForm(@Valid @ModelAttribute("movie") Movie movie, Errors errors) {
      if(errors.hasErrors())
          return "add-movie";
      this.movieRepo.save(movie);
      return "redirect:/view-movies";
    }

    @PostMapping("/edit/{id}")
    public String handleEditReviewForm(@PathVariable Long id, @Valid @ModelAttribute("movie") Movie movie, Errors errors) {
        if(errors.hasErrors())
            return "view-movies";

        try {
            Movie originalMovie = this.movieRepo.findById(id).get();
            updatedOriginalReview(originalMovie, movie);
            this.movieRepo.save(movie);
        } catch (DataIntegrityViolationException e){
            errors.rejectValue("review", "invalidReview", "Review already made");
            return "view-movies";
        }

        return "redirect:/view";
    }

    private void updatedOriginalReview( Movie original, Movie update){
        original.setMovieName(update.getMovieName());
        original.setCategory(update.getCategory());
        original.setEpisodes(update.getEpisodes());
        original.setDescription(update.getDescription());
        original.setRating(update.getRating());

    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id){
        this.movieRepo.deleteById(id);
        return "redirect:/view";
    }

}
