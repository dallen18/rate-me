package info.rateme.rateme.data;

import info.rateme.rateme.models.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    public List<Review> findByUserId(Long userId);

}
