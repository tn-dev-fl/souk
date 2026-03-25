package souk.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import souk.demo.converter.UserConverter;
import souk.demo.dto.UserDTO;
import souk.demo.model.UserModel;
import souk.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final BCryptPasswordEncoder passwordEncoder;

    public Boolean getUserByEmail(String email, String username, String phone) {

        Query query = entityManager
                .createNativeQuery("SELECT check_user_exists(:p_email, :p_username, :p_phone)")
                .setParameter("p_email", email)
                .setParameter("p_username", username)
                .setParameter("p_phone", phone);

        Object result = query.getSingleResult();

        return (Boolean) result;
    }

    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<UserDTO> getAllUsers() {
        return userConverter.toDTOList(userRepository.findAll());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userConverter::toDTO)
                .orElse(null);
    }

    public UserDTO createUser(UserDTO userDTO) {
        UserModel userEntity = userConverter.toEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userConverter.toDTO(userRepository.save(userEntity));
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            return userConverter.toDTO(userRepository.save(user));
        }).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
