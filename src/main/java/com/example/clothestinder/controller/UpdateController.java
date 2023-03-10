package com.example.clothestinder.controller;


import com.example.clothestinder.entity.Tag;
import com.example.clothestinder.entity.User;
import com.example.clothestinder.service.TagService;
import com.example.clothestinder.service.UserService;
import com.example.clothestinder.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.HashMap;
import java.util.Optional;

@Component
@Log4j
public class UpdateController {
    private final UserService userService; //todo
    private final TagService tagService; //todo
    private MyTelegramBot myTelegramBot;
    private MessageUtils messageUtils;
    private HashMap<Long, Integer> stateMap;

    public UpdateController(UserService userService, TagService tagService, MessageUtils messageUtils) {
        this.userService = userService; //Todo
        this.tagService = tagService; //todo
        this.messageUtils = messageUtils;
        this.stateMap = new HashMap<>();
    }

    public void registerBot(MyTelegramBot telegramBot){
        this.myTelegramBot = telegramBot;
    }

    public void processUpdate(Update update){
        var message = update.getMessage();
        Long userId = message.getFrom().getId();

        if (!stateMap.containsKey(userId)) {
            stateMap.put(userId, 0);
        }
        Optional<User> user =  userService.getUserByTelegramId(userId);
        if (stateMap.get(userId) == 0) {
            if (user.isEmpty()) {
                userService.addNewUser(userId);
                setView(messageUtils.generateSendMessageWithText(update,
                        "send login"));
                stateMap.replace(userId, 1);
            } else {
                setView(messageUtils.generateSendMessageWithText(update,
                        "Hello, " + user.get().getLogin()));
                if(message.getText().startsWith("#")){
                    processAddTag(update, message.getText());
                }
            }
        } else if (stateMap.get(userId) == 1) {
            String login = message.getText();
            userService.setLogin(userId, login);
            setView(messageUtils.generateSendMessageWithText(update,
                    "send password"));
            stateMap.replace(userId, 2);
        } else if(stateMap.get(userId) == 2) {
            String password = message.getText();
            userService.setPassword(userId, password);
            setView(messageUtils.generateSendMessageWithText(update,
                    "successfully registered"));
            stateMap.replace(userId, 0);
        }
    }

    private void processAddTag(Update update, String message){
        Optional<Tag> tag = tagService.getTagByName(message);
        if(tag.isEmpty()) {
            String tagToAdd = update.getMessage().getText();
            tagService.addNewTag(tagToAdd);
            setView(messageUtils.generateSendMessageWithText(update,
                    "added tag"));
        } else {
            setView(messageUtils.generateSendMessageWithText(update,
                    "tag " + message + " already exists"));
        }
    }

    public void distributeMessagesByType(Update update){
//        var message = update.getMessage();
//        if(message.getText() != null) {
//            processTextMessage(update);
//            mainService.processTextMessage(update);
//        } else if (message.getDocument() != null ) {
//            processDocMessage(update);
//        } else if (message.getPhoto() != null) {
//            processPhotoMessage(update);
//        } else {
//            setUnsupportedMessageTypeView(update);
//        }
    }

    private void setUnsupportedMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update,
                "Неподдерживаемый тип сообщения!");
        setView(sendMessage);
    }

    private void setView(SendMessage sendMessage) {
        myTelegramBot.sendMessage(sendMessage);
    }

    private void processPhotoMessage(Update update) {
        setView(messageUtils.generateSendMessageWithText(update,
                "Отправлено фото"));
    }

    private void processDocMessage(Update update) {
        setView(messageUtils.generateSendMessageWithText(update,
                "Отправлен документ"));
    }

    private void processTextMessage(Update update) {
        if(update.getMessage().getText().equals("/start")){
            var sendMessage = messageUtils.generateSendMessageWithText(update,
                    "Привет! Я бот clothesTinder, я что-то умею \n" +
                            "Зарегестрируйтесь, чтобы начать работу с ботом clothesTinder \n" +
                            "Введите логин и пароль для регистрации через пробел");
            setView(sendMessage);
        }

//        setView(messageUtils.generateSendMessageWithText(update,
//                "Отправленo текстовое сообщение"));
    }
}
