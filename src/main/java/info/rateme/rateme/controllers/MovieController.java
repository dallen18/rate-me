package info.rateme.rateme.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private MovieRepository movieRepo;

    @Autowired
    public MovieController(MovieRepository movieRepo){this.movieRepo = movieRepo;}

    @GetMapping("/view")
    public String showMovies(Model model){
        List<Movie> movies = (List<Movie>) this.movieRepo.findAll();
        model.addAttribute
    }


}
