package infra.db.jpa_entity;

import domain.model.user.User;
import domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcUserRepository implements UserRepository {
    @Override
    public void save(User user) {
        
    }

    @Override
    public void deleteById(UUID userId) {

    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndActiveTrue(String email) {
        return Optional.empty();
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }
}
