package info.rateme.rateme.data;

import info.rateme.rateme.models.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {

}
