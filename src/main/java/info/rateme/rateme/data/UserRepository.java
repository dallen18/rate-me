package info.rateme.rateme.data;

import info.rateme.rateme.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
