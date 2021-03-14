package info.rateme.rateme.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class Movie{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank(message = "Must enter movie name")
    @Size(min = 2,message = "Movie name must be 2 or more characters")
    private String movieName;

    @NotBlank(message = "Must enter category")
    @Size(min = 2,message = "Category must be 2 or more characters")
    private String category;

    @NotBlank(message = "Must enter episode amount")
    private String episodes;

    @NotBlank(message = "Must enter rating for movie")
    private String rating;

    @NotBlank(message = "Must enter description of movie")
    @Size(min = 6,message = "Must have at least 6 characters")
    private String description;

    private LocalDateTime modified;
    private LocalDateTime created;

    public Movie(String movieName, String category, String episodes, String rating, String description) {
        this.movieName = movieName;
        this.category = category;
        this.episodes = episodes;
        this.rating = rating;
        this.description = description;
    }

    public Movie() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    @PrePersist
    public void onCreate(){
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(){
        this.setModified(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return this.movieName;
    }
}
