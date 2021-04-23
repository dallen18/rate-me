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
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private MovieRepository movieRepo;
    private ReviewRepository reviewRepo;

    @Autowired
    public MovieController(MovieRepository movieRepo, ReviewRepository reviewRepo){
        this.movieRepo = movieRepo;
        this.reviewRepo = reviewRepo;
    }


    @GetMapping("/add")
    public String sendAddMovieForm(Model model) {
        List<Review> reviews = (List<Review>) reviewRepo.findAll();
        model.addAttribute("reviews", reviews);
        model.addAttribute("movie", new Movie());
        return "add-movie";
    }

    @PostMapping("/add")
    public String handleMovieForm(@Valid @ModelAttribute("movie") Movie movie, Errors errors, Model model) {
        if(errors.hasErrors())
            return "add-movie";

        if(movie.getCategory().equals("Movie")) {
            if (!movie.getEpisodes().equals("1")) {
                model.addAttribute("errorMsg", "Movie can only have 1 episode");
                return "add-movie";
            }
        }

        List<Movie> movies = movieRepo.findByMovieName(movie.getMovieName());
        boolean matchfound = movies.stream().anyMatch(m -> m.getGenre().equals(movie.getGenre()) && m.getCategory().equals(movie.getCategory()) && m.getEpisodes().equals(movie.getEpisodes()));
        if(matchfound) {
            model.addAttribute("errorMsg", "Entry Exist");
            return "add-movie";
        }
        this.movieRepo.save(movie);
        return "redirect:/display-movies";
    }

    @GetMapping("/view/{id}")
    public String showMovie(@PathVariable Long id, Model model){
        Movie movie = this.movieRepo.findById(id).get();
        model.addAttribute("movie", movie);
        return "edit-movie";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id){
        this.movieRepo.deleteById(id);
        return "redirect:/display-movies";
    }

    @PostMapping("/edit/{id}")
    public String handleEditMovieForm(@PathVariable Long id, @Valid @ModelAttribute("movie") Movie movie, Errors errors, Model model) {
        if(errors.hasErrors())
            return "edit-movie";

        if(movie.getCategory().equals("Movie")) {
            if (!movie.getEpisodes().equals("1")) {
                model.addAttribute("errorMsg", "Movie can only have 1 episode");
                return "add-movie";
            }
        }

        List<Movie> movies = movieRepo.findByMovieName(movie.getMovieName());
        boolean matchfound = movies.stream().anyMatch(m -> m.getGenre().equals(movie.getGenre()) && m.getCategory().equals(movie.getCategory()) && m.getEpisodes().equals(movie.getEpisodes()));
        if(matchfound) {
            model.addAttribute("errorMsg", "Entry Exist");
            return "add-movie";
        }

        try {
            Movie originalMovie = this.movieRepo.findById(id).get();
            updatedOriginalMovie(originalMovie, movie);
            this.movieRepo.save(movie);
        } catch (DataIntegrityViolationException e){
            errors.rejectValue("movie", "invalidMovie", "Invalid movie choice");
            return "edit-movie";
        }

        this.movieRepo.save(movie);
        return "redirect:/display-movies";

    }

    private void updatedOriginalMovie(Movie original, Movie update) {
        original.setMovieName(update.getMovieName());
        original.setCategory(update.getCategory());
        original.setEpisodes(update.getEpisodes());
        original.setGenre(update.getGenre());
    }

}
