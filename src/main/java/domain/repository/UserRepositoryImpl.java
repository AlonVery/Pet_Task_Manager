package domain.repository;

import domain.model.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryImpl {

    User createDefaultUser(String userName, String email, String passwordHash, PasswordEncoder encoder);

    boolean save(User user);

    void delete(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAndActiveTrue(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    List<User> getAllUsers();
}
