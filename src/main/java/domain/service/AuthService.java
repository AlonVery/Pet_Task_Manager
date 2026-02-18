package domain.service;

import domain.exception.InvalidCredentialsException;
import domain.exception.UserIsNotActiveException;
import domain.exception.UserNotFoundException;
import domain.model.user.User;
import domain.model.user.UserRole;
import domain.model.user.UserStatus;
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
        if(userRepository.existsByEmail(email)) {
            throw new UserIsNotActiveException("Email already exists");
        }
        userRepository.save(User.create(username, email, password, UserRole.USER, encoder));
    }

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not fount by: " + email));
        if(user.getUserStatus() != UserStatus.ACTIVE) {
            throw new UserIsNotActiveException(email);
        }
        if (!user.isPasswordValid(rawPassword, encoder)) {
            throw new InvalidCredentialsException();
        }
        return ("User logged in successfully!");
    }

}

