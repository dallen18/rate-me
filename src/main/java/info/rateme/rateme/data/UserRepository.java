package info.rateme.rateme.data;

import info.rateme.rateme.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
    User findByUsername(String username);

}
