package domain.service;

import domain.model.user.User;
import domain.repository.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean save(User user) {
        userRepository.save(user);
        return true;
    }

    public void delete(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

}
