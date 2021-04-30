package info.rateme.rateme.security;

import info.rateme.rateme.data.UserRepository;
import info.rateme.rateme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserDataLoader implements CommandLineRunner {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDataLoader(UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception{
        /*User user = new User("Deontae", "Allen", "dallen18", "dallen18@neiu.edu", passwordEncoder.encode("password"));
        user.setRoles(Set.of(User.Role.ROLE_ADMIN));
        user.setEnabled(true);
        userRepo.save(user);

        User user1 = new User("Timothy", "Torres", "ttorres", "ttorres@neiu.edu", passwordEncoder.encode("password"));
        user1.setRoles(Set.of(User.Role.ROLE_USER));
        user1.setEnabled(true);
        userRepo.save(user1);*/

    }

}
