package domain.service;

import domain.model.user.User;
import domain.model.user.UserRole;
import domain.repository.UserRepositoryImpl;

import java.util.UUID;

public class UserService {
    private final UserRepositoryImpl userRepository;
    private User userInstance;

    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    public User create(
            String userName,
            String email,
            String passwordHash,
            UserRole userRole) {
        if (userRepository.findByUsername(userName).isPresent()
                || userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        return userInstance.createUser(
                userName,
                email,
                passwordHash,
                userRole);
    }

    public void delete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.delete(user);
    }

}
