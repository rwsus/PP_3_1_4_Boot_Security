package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void saveUser(String name, String lastName, int age);
    void updateUser(long id, User updatedUser);
    User findUserById(long id);

    void removeUserById(long id);
}
