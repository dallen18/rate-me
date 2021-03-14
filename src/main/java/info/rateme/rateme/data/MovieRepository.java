package info.rateme.rateme.data;

import info.rateme.rateme.models.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long> {

}
