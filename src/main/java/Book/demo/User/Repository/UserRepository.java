package Book.demo.User.Repository;

import Book.demo.User.Entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserDetails> findByUserGmail(String userGmail);
}
