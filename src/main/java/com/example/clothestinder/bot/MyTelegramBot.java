package com.example.clothestinder.bot;

import com.example.clothestinder.TagAlreadyExistsException;
import com.example.clothestinder.controller.UpdateController;
import com.example.clothestinder.dao.TelegramUserStateDAO;
import com.example.clothestinder.entity.TelegramUserState;
import com.example.clothestinder.entity.User;
import com.example.clothestinder.service.UserService;
import com.example.clothestinder.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.Optional;

@Log4j
@Component
public class MyTelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String token;
    private final UpdateController updateController;
    private TelegramUserStateDAO telegramUserStateDAO;
    private UserService userService;

    public MyTelegramBot(UpdateController updateController, TelegramUserStateDAO telegramUserStateDAO, UserService userService) {
        this.updateController = updateController;
        this.telegramUserStateDAO = telegramUserStateDAO;
        this.userService = userService;
    }

    private Long k;

    @PostConstruct
    public void init() {
        updateController.registerBot(this);
        k = 0L;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {

        var message = update.getMessage();
        Long userId = update.getMessage().getFrom().getId();
        User user;
        Optional<User> optUser = userService.getUserByTelegramId(userId);
        if (optUser.isEmpty()) {
            user = userService.addNewUser(userId);
        } else {
            user = optUser.get();
        }
        TelegramUserState state = telegramUserStateDAO.getTelegramUserStateByUserId(user.getId());
        try {
            if (state == null) { // TODO если не будет работать

                if (state == null) {
                    state = new TelegramUserState();
                    state.setUser(user);
                    state.setUserState(UserState.UNKNOWN);
                    telegramUserStateDAO.save(state);
                }
                this.sendMessage(MessageUtils.generateSendMessageWithText(update,
                        "Hi! Please register to continue working with bot"));
            } else if (message.getText().equals("/register")) {
                state.setUserState(UserState.SETTING_LOGIN);
                telegramUserStateDAO.save(state);
                this.sendMessage(MessageUtils.generateSendMessageWithText(update,
                        "Send login"));
            } else if (state.getUserState() == UserState.SETTING_LOGIN) {
                userService.setLogin(userId, message.getText());
                state.setUserState(UserState.SETTING_PASSWORD);
                telegramUserStateDAO.save(state);
                this.sendMessage(MessageUtils.generateSendMessageWithText(update,
                        "Send password"));
            } else if (state.getUserState() == UserState.SETTING_PASSWORD) {
                userService.setPassword(userId, message.getText());
                state.setUserState(UserState.FREE);
                telegramUserStateDAO.save(state);
                this.sendMessage(MessageUtils.generateSendMessageWithText(update,
                        "Hello, " + user.getLogin()));
            } else if (message.getText().equals("/add_tag")) {
                state.setUserState(UserState.ADDING_TAG);
                telegramUserStateDAO.save(state);
                this.sendMessage(MessageUtils.generateSendMessageWithText(update,
                        "Send tag"));
            } else if (state.getUserState() == UserState.ADDING_TAG) {
                updateController.processAddTag(message.getText());
                this.sendMessage(MessageUtils.generateSendMessageWithText(update,
                        "Tag successfully added"));
                state.setUserState(UserState.FREE);
                telegramUserStateDAO.save(state);
            } else if (state.getUserState() == UserState.FREE) {
                user = userService.getUserByTelegramId(userId).get();
                this.sendMessage(MessageUtils.generateSendMessageWithText(update,
                        "Hello, " + user.getLogin()));
            } else if (state.getUserState() == UserState.UNKNOWN) {
                this.sendMessage(MessageUtils.generateSendMessageWithText(update,
                        "Hi! Please register to continue working with bot"));
            }
        } catch (
                TagAlreadyExistsException e) {
            this.sendMessage(MessageUtils.generateSendMessageWithText(update,
                    "tag " + update.getMessage().getText() + " already exists"));
            state.setUserState(UserState.FREE);
            telegramUserStateDAO.save(state);
        }
    }

    public void sendMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                log.error(e);
            }
        }
    }
}
