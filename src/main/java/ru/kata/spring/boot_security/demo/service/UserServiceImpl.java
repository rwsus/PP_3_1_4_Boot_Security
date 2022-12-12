package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Lazy
    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Integer id, User updatedUser) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        User userToBeUpdated = findUserById(id);

        userToBeUpdated.setLastname(updatedUser.getLastname());
        userToBeUpdated.setAge(updatedUser.getAge());
        userToBeUpdated.setEmail(updatedUser.getEmail());
        userToBeUpdated.setPassword(updatedUser.getPassword());
        userToBeUpdated.setRoles(updatedUser.getRoles());
        userToBeUpdated.setName(updatedUser.getName());
        userRepository.save(userToBeUpdated);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userRepository.findUserByUsername(email);
    }

    @Override
    @Transactional
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = findUserByEmail(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        //Hibernate.initialize(user.getRoles());
        return user;
    }

}
