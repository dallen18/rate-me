package info.rateme.rateme.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
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

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();

    private LocalDateTime modified;

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    private LocalDateTime created;

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

    public Long getId() {
        return id;
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

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return this.movieName;
    }
}
