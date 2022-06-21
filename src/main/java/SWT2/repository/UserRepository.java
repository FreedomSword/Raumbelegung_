package SWT2.repository;

import SWT2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u from User u WHERE u.email = ?1")
    public User getUsersByUsername(String username);

}
