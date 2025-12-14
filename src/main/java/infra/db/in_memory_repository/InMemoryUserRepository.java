package infra.db.in_memory_repository;

import domain.model.user.User;
import domain.model.user.UserStatus;
import domain.repository.UserRepositoryImpl;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepositoryImpl {

    private final Map<UUID, User> users = new ConcurrentHashMap<>();
    private Object lock;

    @Override
    public void save(User user) {
        if (users.containsValue(user.getEmail())) {
            throw new IllegalArgumentException("User already exists");
        }
        synchronized (lock) {
            users.put(user.getUserId(), user);
        }
    }

    @Override
    public void delete(User user) {
        if (users.containsKey(user.getUserId())) {
            users.remove(user.getUserId());
        }
    }

    @Override
    public Optional<User> findById(UUID id) {
        return users.values().stream().
                filter(user -> user.getUserId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();

    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.values().stream()
                .filter(u -> u.getUserName().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmailAndActiveTrue(String email) {
        Optional<User> user = findByEmail(email);
        return user.filter(u -> u.getUserStatus() == UserStatus.ACTIVE);
    }

    @Override
    public boolean existsByEmail(String email) {
        return users.values().stream().anyMatch(u -> u.getEmail().equals(email));
    }

    @Override
    public boolean existsByUsername(String username) {
        return users.values().stream().anyMatch(u -> u.getUserName().equals(username));
    }

}
