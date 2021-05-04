package info.rateme.rateme.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Movie {

    public void setId(Long id) {
        this.id = id;
    }

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

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    private LocalDateTime modified;

    private LocalDateTime created;

    public Movie() {
    }

    public Movie(@NotBlank(message = "Must enter movie name") @Size(min = 2, message = "Review name must be 2 or more characters") String movieName,
                 @NotBlank(message = "Must enter category") @Size(min = 2, message = "Category must be 2 or more characters") String category,
                 @NotBlank(message = "Must enter genre") @Size(min = 2, message = "Genre must be 2 or more characters") String genre,
                 @NotBlank(message = "Must enter episode amount") String episodes, Long id) {
        this.movieName = movieName;
        this.category = category;
        this.genre = genre;
        this.episodes = episodes;
        this.id = id;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
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
