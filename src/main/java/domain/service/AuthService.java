package domain.service;

import domain.model.user.User;
import domain.model.user.UserRole;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    public AuthService(UserRepository userRepository, PasswordEncoder encoder) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public void register(String username, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email already in use");
        }
        User user = User.create(username, email, password, UserRole.USER, encoder);
        userRepository.save(user);
    }

    public boolean login(String name, String rawPassword) {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.isPasswordValid(rawPassword, encoder)) {
            System.out.println("For this user: " + name + " invalid password" + rawPassword);
            return false;
        }
        System.out.println("User " + name + " logged in successfully!");
        return true;
    }

}

