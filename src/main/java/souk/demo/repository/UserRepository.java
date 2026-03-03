package souk.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import souk.demo.dto.UserDTO;
import souk.demo.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    // You can add custom queries here if needed later
	
}
