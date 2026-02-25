package souk.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import souk.demo.dto.UserDTO;
import souk.demo.model.Users;
import souk.demo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

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
        Users userEntity = convertToEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword())); // hash password
        return convertToDTO(userRepository.save(userEntity));
    }

    // Update existing user
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            // Password update can be handled separately for security reasons
            return convertToDTO(userRepository.save(user));
        }).orElse(null);
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Conversion methods
    private UserDTO convertToDTO(Users user) {
        return new UserDTO(user.getUsername(), user.getEmail());
    }

    private Users convertToEntity(UserDTO userDTO) {
        Users user = new Users();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        return user;
    }
}
