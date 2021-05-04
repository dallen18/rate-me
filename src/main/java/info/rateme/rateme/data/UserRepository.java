package info.rateme.rateme.data;

import info.rateme.rateme.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
    User findByUsername(String username);
    @Override
    Optional<User> findById(Long id);
}
