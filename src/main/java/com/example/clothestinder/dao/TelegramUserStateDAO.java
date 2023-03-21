package com.example.clothestinder.dao;

import com.example.clothestinder.entity.TelegramUserState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserStateDAO extends JpaRepository<TelegramUserState, Long> {
    TelegramUserState getTelegramUserStateByUserId(Long userId);
}
