package info.rateme.rateme.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Must enter movie name")
    @Size(min = 2,message = "Review name must be 2 or more characters")
    private String movieName;

    @NotBlank(message = "Must enter category")
    @Size(min = 2,message = "Category must be 2 or more characters")
    private String category;

    @NotBlank(message = "Must enter genre")
    @Size(min = 2,message = "Genre must be 2 or more characters")
    private String genre;

    @NotBlank(message = "Must enter episode amount")
    private String episodes;

    public Movie() {
    }

    public Movie(@NotBlank(message = "Must enter movie name") @Size(min = 2, message = "Review name must be 2 or more characters") String movieName,
                 @NotBlank(message = "Must enter category") @Size(min = 2, message = "Category must be 2 or more characters") String category,
                 @NotBlank(message = "Must enter genre") @Size(min = 2, message = "Genre must be 2 or more characters") String genre,
                 @NotBlank(message = "Must enter episode amount") String episodes) {
        this.movieName = movieName;
        this.category = category;
        this.genre = genre;
        this.episodes = episodes;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return this.movieName;
    }
}
