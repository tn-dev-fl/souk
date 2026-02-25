package souk.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import souk.demo.entity.User;

public interface UserInterface extends JpaRepository<User, Long>  {



}