package souk.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import souk.demo.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    // You can add custom queries here if needed later
}
