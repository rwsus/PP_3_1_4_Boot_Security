package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    List<User> getAllUsers();

    void saveUser(String email, String password, Set<Role> roles, String name, String lastName, int age);
    void updateUser(Long id, User updatedUser);

    User findUserById(Long id);
    User findUserByEmail(String email);


    void removeUserById(long id);

}
