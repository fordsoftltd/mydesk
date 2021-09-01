package fordsoft.tech.mydesk.repo;

import fordsoft.tech.mydesk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
