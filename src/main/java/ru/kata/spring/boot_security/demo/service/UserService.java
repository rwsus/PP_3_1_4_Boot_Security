package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public interface UserService{
    List<User> getAllUsers();

    void saveUser(User user);
    void updateUser(Integer id, User updatedUser);
    User findUserById(Integer id);
    User findUserByEmail(String email);

    void deleteUserById(Integer id);

}
