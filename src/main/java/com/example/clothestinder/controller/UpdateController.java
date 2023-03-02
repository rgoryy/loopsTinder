package com.example.clothestinder.controller;

import com.example.clothestinder.service.MainService;
import com.example.clothestinder.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Log4j
public class UpdateController {
    private final MainService mainService; //todo
    private MyTelegramBot myTelegramBot;
    private MessageUtils messageUtils;

    public UpdateController(MainService mainService, MessageUtils messageUtils) {
        this.mainService = mainService; //Todo
        this.messageUtils = messageUtils;
    }

    public void registerBot(MyTelegramBot telegramBot){
        this.myTelegramBot = telegramBot;
    }

    public void processUpdate(Update update){
        if(update == null) {
            log.error("Received update is null");
        }

        if(update.getMessage() != null) {
            distributeMessagesByType(update);
        } else {
            log.error("Received unsupported message type" + update);
        }
    }

    public void distributeMessagesByType(Update update){
        var message = update.getMessage();
        if(message.getText() != null) {
            processTextMessage(update);
            mainService.processTextMessage(update);
        } else if (message.getDocument() != null ) {
            processDocMessage(update);
        } else if (message.getPhoto() != null) {
            processPhotoMessage(update);   
        } else {
            setUnsupportedMessageTypeView(update);
        }
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
                    "Привет! Я бот clothesTinder, я что-то умею");
            setView(sendMessage);
        }

        setView(messageUtils.generateSendMessageWithText(update,
                "Отправленo текстовое сообщение"));
    }
}
