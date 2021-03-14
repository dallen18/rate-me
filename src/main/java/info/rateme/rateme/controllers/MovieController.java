package info.rateme.rateme.controllers;

import info.rateme.rateme.data.MovieRepository;
import info.rateme.rateme.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping
    public String handleMovieForm(@Valid @ModelAttribute("movie") Movie movie, Errors errors) {
      if(errors.hasErrors())
          return "add-movie";
      this.movieRepo.save(movie);
      return "redirect:/view-movies";
    }
}
