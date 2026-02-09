package domain.repository;

import domain.model.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    void save(User user);

    boolean deleteById(UUID userId);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAndActiveTrue(String email);

    boolean existsByEmail(String email);

    List<User> getAllUsers();
}
