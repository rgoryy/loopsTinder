package com.example.clothestinder.dao;

import com.example.clothestinder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {
    User getUserByTelegramId(Long telegramId);
}
