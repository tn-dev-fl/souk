package souk.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import souk.demo.dto.UserDTO;

import souk.demo.model.UserModel;
import souk.demo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

	@PersistenceContext
    private EntityManager entityManager;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public Boolean getUserByEmail(String email,String username,String phone) {


        Query query = entityManager
                .createNativeQuery("CALL check_user_exists(:p_email, :p_username, :p_phone)")
                .setParameter("p_email", email)
                .setParameter("p_username", username)
                .setParameter("p_phone", phone);
        		

        Object result = query.getSingleResult();

        return (Boolean) result;
    }
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Get all users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get user by ID
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Create new user
    public UserDTO createUser(UserDTO userDTO) {
        UserModel userEntity = convertToEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return convertToDTO(userRepository.save(userEntity));
    }

    // Update existing user
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());

            return convertToDTO(userRepository.save(user));
        }).orElse(null);
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertToDTO(UserModel user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(),null);
    }

    private UserModel convertToEntity(UserDTO userDTO) {
        UserModel user = new UserModel();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        return user;
    }
}
