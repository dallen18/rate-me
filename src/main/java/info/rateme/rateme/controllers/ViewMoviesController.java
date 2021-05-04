package info.rateme.rateme.controllers;

import info.rateme.rateme.data.MovieRepository;
import info.rateme.rateme.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/display-movies")
public class ViewMoviesController {

    private MovieRepository movieRepo;

    @Autowired
    public ViewMoviesController(MovieRepository movieRepo){
        this.movieRepo = movieRepo;
    }

    @GetMapping
    public String showMovies(Model model){
        List<Movie> movies = (List<Movie>) this.movieRepo.findAll();
        model.addAttribute("movies", movies);
        return "display-movies";
    }

}
