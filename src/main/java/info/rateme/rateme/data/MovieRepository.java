package info.rateme.rateme.data;

import info.rateme.rateme.models.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    public List<Movie> findByMovieName(String movieName);
}
