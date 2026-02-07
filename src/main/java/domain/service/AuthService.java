package domain.service;

import domain.exception.InvalidCredentialsException;
import domain.exception.UserNotFoundException;
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

    public User register(String username, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException();
        }
        User user = User.create(username, email, password, UserRole.USER, encoder);
        userRepository.save(user);
        return user;
    }

    public String login(String name, String rawPassword) {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new UserNotFoundException(name));
        if (!user.isPasswordValid(rawPassword, encoder)) {
            throw new InvalidCredentialsException();
        }
        return ("User " + name + " logged in successfully!");
    }

}

