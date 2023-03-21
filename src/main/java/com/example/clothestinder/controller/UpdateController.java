package com.example.clothestinder.controller;


import com.example.clothestinder.bot.MyTelegramBot;
import com.example.clothestinder.entity.User;
import com.example.clothestinder.service.RequestService;
import com.example.clothestinder.service.TagService;
import com.example.clothestinder.service.UserService;
import com.example.clothestinder.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@Log4j
public class UpdateController {
    private final UserService userService; //todo
    private final TagService tagService; //todo
    private final RequestService requestService; //todo
    private MyTelegramBot myTelegramBot;
    private final MessageUtils messageUtils;

    public UpdateController(UserService userService, TagService tagService, RequestService requestService, MessageUtils messageUtils) {
        this.userService = userService; //Todo
        this.tagService = tagService; //todo
        this.requestService = requestService; //todo
        this.messageUtils = messageUtils;
    }

    public void registerBot(MyTelegramBot telegramBot) {
        this.myTelegramBot = telegramBot;
    }

    private void processAddRequest(Update update, User user) {
        requestService.addNewRequest(user);
        setView(messageUtils.generateSendMessageWithText(update,
                "added loop request"));
    }


    public void processAddTag(String message) {
        tagService.addNewTag(message);
    }

    public void distributeMessagesByType(Update update) {
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
        if (update.getMessage().getText().equals("/start")) {
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
