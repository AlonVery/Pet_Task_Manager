package domain.service;

import domain.model.user.User;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepositoryImpl;


public class AuthService {
    private final UserRepositoryImpl userRepository;
    private UserService userService;
    private final PasswordEncoder encoder;

    public AuthService(UserRepositoryImpl userRepository, UserService userService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.encoder = encoder;
    }

    public AuthService(UserRepositoryImpl userRepository, PasswordEncoder encoder) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public void register(
            String userName,
            String email,
            String passwordHash) {
        if(userRepository.existsByUsername(userName)) {
            throw new IllegalArgumentException("Username already exists");
        }
        User user = userRepository.createDefaultUser(userName, email, passwordHash, encoder);
        // log registration
        System.out.println("User register : " + userName);
        userService.save(user);
    }

    public boolean login(String name, String rawPassword) {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isPasswordValid(rawPassword, encoder)) {
            System.out.println("For this user: " + name  + " invalid password" +  rawPassword);
            return false;
        }
        System.out.println("User "+ name + " logged in successfully!");
        return true;
    }

}

