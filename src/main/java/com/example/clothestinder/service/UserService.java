package com.example.clothestinder.service;

import com.example.clothestinder.dao.UserDAO;
import com.example.clothestinder.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User addNewUser(long telegramId) {
        User user = new User();
        user.setTelegramId(telegramId);
        user.setLogin(""); //TODO fix this
        user.setPassword("");
        userDAO.save(user);
        return user;
    }

    public Optional<User> getUserByTelegramId(Long telegramId) {
        User user = userDAO.getUserByTelegramId(telegramId);
        if (user == null) {
            return Optional.empty();
        } else {
            return Optional.of(user);
        }
    }

    public void setLogin(Long telegramId, String login) {
        User user = userDAO.getUserByTelegramId(telegramId);
        user.setLogin(login);
        userDAO.save(user);
    }

    public void setPassword(Long telegramId, String password) {
        User user = userDAO.getUserByTelegramId(telegramId);
        user.setPassword(password);
        userDAO.save(user);
    }
}
