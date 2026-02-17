package infra.db.in_memory_repository;

import domain.model.user.User;
import domain.model.user.UserStatus;
import domain.repository.UserRepository;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {

    private final Map<UUID, User> users = new ConcurrentHashMap<>();
    private final Map<String, UUID> emailIndex = new ConcurrentHashMap<>();
    private final Map<String, UUID> usernameIndex = new ConcurrentHashMap<>();


    @Override
    public void save(User user) {
        users.put(user.getUserId(), user);
        emailIndex.put(user.getEmail(), user.getUserId());
        usernameIndex.put(user.getUserName(), user.getUserId());
    }

    @Override
    public boolean deleteById(UUID userId) {
        User removed = users.remove(userId);
        if (removed != null) {
            emailIndex.remove(removed.getEmail());
            usernameIndex.remove(removed.getUserName());
        }
        return false;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(u -> Objects.equals(u.getEmail(), email))
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
    public List<User> getAllUsers() {
        return List.copyOf(users.values());
    }
    
}
