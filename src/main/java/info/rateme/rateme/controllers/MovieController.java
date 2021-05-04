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
@RequestMapping("/movie")
public class MovieController {

    private MovieRepository movieRepo;
    private ReviewRepository reviewRepo;
    private UserRepository userRepo;

    @Autowired
    public MovieController(MovieRepository movieRepo, ReviewRepository reviewRepo, UserRepository userRepo){
        this.userRepo = userRepo;
        this.movieRepo = movieRepo;
        this.reviewRepo = reviewRepo;
    }

    @PostMapping("/add")
    public String handleMovieForm(@ModelAttribute(value = "movie") Movie movie, Errors errors, @AuthenticationPrincipal User user, Model model) {
        if (errors.hasErrors()) {
            return "add-movie";
        }
        if(movie.getCategory().equals("Movie")) {
            if (!movie.getEpisodes().equals("1")) {
                model.addAttribute("errorMsg", "Movie can only have 1 episode");
                return "add-movie";
            }
        }
        List<Movie> movies = movieRepo.findByMovieName(movie.getMovieName());
        boolean matchfound = movies.stream().anyMatch(m -> m.getGenre().equals(movie.getGenre()));
            if(matchfound) {
                model.addAttribute("errorMsg", "Entry Exist");
                return "add-movie";
            }
        try {
            movie.setUser(user);
            this.movieRepo.save(movie);
        } catch (DataIntegrityViolationException e) {
            return "add-movie";
        }
        return "redirect:/display-movies";
    }

    @GetMapping("/add")
    public String sendAddMovieForm(@ModelAttribute("movie") Movie movie, Model model, @ModelAttribute("used") User user){
        model.addAttribute("movie", new Movie());
        model.addAttribute("user_id", user.getId());
        return "add-movie";
    }

    /*@PostMapping("/add")
    public String handleMovieForm(@Valid @ModelAttribute("movie") Movie movie, Errors errors, Model model, Long user_id) {
        if(errors.hasErrors())
            return "add-movie";
        }
    List<Movie> movies = movieRepo.findByMovieName(m.getMovieName());
    boolean matchfound = movies.stream().anyMatch(m -> m.getGenre().equals(m.getGenre()) && m.getCategory().equals(movie.getCategory()) && m.getEpisodes().equals(movie.getEpisodes())){
    if(matchfound) {
        model.addAttribute("errorMsg", "Entry Exist");
            return "add-movie";
        }
        this.movieRepo.save(movie);
        return "redirect:/display-movies";
    }*/

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
        boolean matchfound = movies.stream().anyMatch(m -> m.getGenre().equals(movie.getGenre()) && m.getCategory().equals(movie.getCategory()));
        if(matchfound) {
            model.addAttribute("errorMsg", "Entry Exists");
            return "add-movie";
        }

        try {
            Movie originalMovie = this.movieRepo.findById(id).get();
            updatedOriginalMovie(originalMovie, movie);
            this.movieRepo.save(originalMovie);
        } catch (DataIntegrityViolationException e){
            errors.rejectValue("movieName", "invalidMovie", "Invalid movie choice");
            return "edit-movie";
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
