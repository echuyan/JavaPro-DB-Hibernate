package org.example.service;

import org.example.entity.UserEntity;
import org.example.repository.EntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final EntityRepository repository;

    public UserService(EntityRepository repository) {
        this.repository = repository;
    }

    public UserEntity createUser(String username) {
        return repository.save(new UserEntity(username));
    }

    public UserEntity getUserById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found, id = " + id));
    }

    public List<UserEntity> getAllUsers() {
        return repository.findAll();
    }

    public UserEntity updateUser(Long id, String username) {
        UserEntity userEntity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found, id = " + id));
        userEntity.setUsername(username);
        return repository.save(userEntity);
    }

    public void deleteUser(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("User not found, id = " + id);
        }
        repository.deleteById(id);
    }

}
