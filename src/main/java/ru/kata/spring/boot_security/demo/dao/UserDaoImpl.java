package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("select a from User a", User.class);
        return query.getResultList();
    }

    @Override
    public void saveUser(String name, String lastName, int age) {
        entityManager.persist(new User(name, lastName, age));
    }

    @Override
    public void updateUser(long id, User updatedUser) {
        User userToBeUpdated = findUserById(id);
        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setLastname(updatedUser.getLastname());
        userToBeUpdated.setAge(updatedUser.getAge());
    }

    @Override
    public User findUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void removeUserById(long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }
}
