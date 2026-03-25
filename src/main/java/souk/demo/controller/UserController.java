package souk.demo.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import souk.demo.dto.AuthResponse;
import souk.demo.dto.UserDTO;
import souk.demo.model.UserModel;
import souk.demo.security.JwtUtil;
import souk.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.debug("Request received to fetch all users.");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        logger.debug("Fetching user details for ID: {}", id);
        UserDTO userDTO = userService.getUserById(id);

        if (userDTO == null) {
            logger.warn("User lookup failed: No user found with ID {}", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userDTO);
    }

    // Create new user
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        logger.info("New registration attempt for email: {}", userDTO.getEmail());

        boolean userExists = userService.getUserByEmail(userDTO.getEmail(), userDTO.getUsername(), userDTO.getPhone());

        if (!userExists) {
            UserDTO savedUser = userService.createUser(userDTO);
            logger.info("Successfully registered user: {} with ID: {}", savedUser.getUsername(), savedUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        }

        logger.warn("Registration rejected: User already exists (Email: {})", userDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        logger.info("Updating user profile for ID: {}", id);
        UserDTO updatedUser = userService.updateUser(id, userDTO);

        if (updatedUser == null) {
            logger.error("Update failed: User ID {} does not exist.", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedUser);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.warn("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO request) {
        try {
            logger.info("Login attempt for user: {}", request.getUsername());

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));

            String token = jwtUtil.generateToken((UserModel) auth.getPrincipal());
            logger.info("Login successful for user: {}", request.getUsername());

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (AuthenticationException e) {

            logger.error("Authentication failed for user: {} - Reason: {}",
                    request.getUsername(), e.getMessage());
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}