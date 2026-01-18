package org.example.service;

import org.example.dao.UserDao;
import org.example.datamodel.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(User user){
        int rows = userDao.createUser(user);
        if (rows != 1){
            throw new IllegalStateException("Not created " + rows);
        }
    }

    public User getUserById(long id){
        return userDao.getUser(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id=" + id + " not found"));
    }

    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }

    public void updateUser(User user){
        int rows = userDao.updateUser(user);
        if (rows != 1){
            throw new IllegalStateException("Not updated " + rows);
        }
    }

    public void deleteUser(long id){
        int rows = userDao.deleteUser(id);
        if (rows != 1){
            throw new IllegalStateException("Not deleted " + rows);
        }
    }

}
