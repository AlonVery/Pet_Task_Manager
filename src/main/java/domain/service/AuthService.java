package domain.service;

import domain.model.user.User;
import domain.model.user.UserRole;
import domain.repository.UserRepositoryImpl;

public class AuthService {
    private UserService userService;
    private final UserRepositoryImpl userRepository;
    private User userInstance;

    public AuthService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    public void register(
            String userName,
            String email,
            String passwordHash
    ) {
        User registeredUser = userService.create(userName, email, passwordHash, UserRole.USER);
        userRepository.save(registeredUser);
        // log registration
    }

    public boolean login(String name, String password) {
        if(userRepository.existsByUsername(name)) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = userRepository.findByUsername(name).orElseThrow(() -> new RuntimeException("User not found"));
        return userInstance.isPasswordValid(user, password);
        // log login
    }

}

