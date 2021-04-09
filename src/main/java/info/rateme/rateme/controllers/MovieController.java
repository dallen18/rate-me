package info.rateme.rateme.controllers;

import info.rateme.rateme.data.MovieRepository;
import info.rateme.rateme.models.Movie;
import info.rateme.rateme.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private MovieRepository movieRepo;

    @Autowired
    public MovieController(MovieRepository movieRepo){this.movieRepo = movieRepo;}

    @GetMapping("/add")
    public String sendAddReviewForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "add-movie";
    }

    @PostMapping("/add")
    public String handleReviewForm(@Valid @ModelAttribute("review") Movie movie, Errors errors) {
      if(errors.hasErrors())
          return "add-review";

     if(movie.getCategory().equals("MovieController"))
          if(! movie.getEpisodes().equals("1"))
              return "add-review";

      this.movieRepo.save(movie);
      return "redirect:/display-movies";
    }

    @GetMapping("/view/{id}")
    public String showReview(@PathVariable Long id, Model model){
        Movie movie = this.movieRepo.findById(id).get();
        model.addAttribute("movie", movie);
        return "view-movie";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id){
        this.movieRepo.deleteById(id);
        return "redirect:/display-movies";
    }

    @PostMapping("/edit/{id}")
    public String handleEditMovieForm(@PathVariable Long id, @Valid @ModelAttribute("movie") Movie movie, Errors errors) {
        if(errors.hasErrors())
            return "view-movie";

        try {
            Movie originalMovie = this.movieRepo.findById(id).get();
            updatedOriginalMovie(originalMovie, movie);
            this.movieRepo.save(movie);
        } catch (DataIntegrityViolationException e){
            errors.rejectValue("movie", "invalidMovie", "Movie already made");
            return "view-movie";
        }

        return "redirect:/display-movies";
    }

    private void updatedOriginalMovie(Movie original, Movie update) {
        original.setMovieName(update.getMovieName());
        original.setCategory(update.getCategory());
        original.setEpisodes(update.getEpisodes());
        original.setGenre(update.getGenre());
    }

}
