package infra.db.in_memory_repository;

import domain.exception.UserAlreadyExistException;
import domain.model.user.User;
import domain.model.user.UserRole;
import domain.model.user.UserStatus;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepositoryImpl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepositoryImpl {

    private final Map<UUID, User> users = new ConcurrentHashMap<>();
    private final Object lock =  new Object();

    @Override
    public User createDefaultUser(String userName, String email, String passwordHash, PasswordEncoder encoder) {
        return User.createUser(userName, email, passwordHash, UserRole.USER, encoder);
    }

    @Override
    public boolean save(User user) {
        if (existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User already exists");
        }
        synchronized (lock) {
            users.put(user.getUserId(), user);
            return true;
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

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
