package domain.service;

import domain.model.user.User;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepositoryImpl;
import infra.security.PasswordEncoderBCrypt;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepositoryImpl userRepository;
    PasswordEncoder encoder = new PasswordEncoderBCrypt();

    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    public User create(
            String userName,
            String email,
            String passwordHash) {
        if (userRepository.findByUsername(userName).isPresent()
                || userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        return userRepository.createDefaultUser(userName, email, passwordHash, encoder);
    }

    public boolean save(User user) {
        userRepository.save(user);
        return true;
    }


    public void delete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.delete(user);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

}
