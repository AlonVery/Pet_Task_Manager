package domain.service;

import domain.model.user.User;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepositoryImpl;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthService {
    private final UserRepositoryImpl userRepository;
    private final UserService userService;
    final PasswordEncoder encoder;

    public User register(
            String userName,
            String email,
            String passwordHash) {
        User user = userRepository.createDefaultUser(userName, email, passwordHash, encoder);
        // log registration
        System.out.println("User register : " + userName);
        userService.save(user);
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

