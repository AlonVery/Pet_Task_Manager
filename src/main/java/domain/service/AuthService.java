package domain.service;

import domain.model.user.User;
import domain.model.user.UserRole;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepositoryImpl;

public class AuthService {
    private final UserRepositoryImpl userRepository;
    private final User userInstance = new User();
    final PasswordEncoder encoder;

    public AuthService(UserRepositoryImpl userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User register(
            String userName,
            String email,
            String passwordHash) {
        User user = User.createUser(userName, email, passwordHash, UserRole.USER, encoder);
        userRepository.save(user);
        // log registration
        System.out.println("User register : " + userName);
        return user;
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

