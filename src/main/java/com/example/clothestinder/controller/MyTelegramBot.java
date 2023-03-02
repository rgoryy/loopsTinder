package com.example.clothestinder.controller;

import com.example.clothestinder.service.impl.MainServiceImpl;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Log4j
@Component
public class MyTelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}") private String botName;
    @Value("${bot.token}") private String token;
   // private static final Logger log = Logger.getLogger(MyTelegramBot.class);
    private UpdateController updateController;

    public MyTelegramBot(UpdateController updateController){
        this.updateController = updateController;
    }

    @PostConstruct
    public void init(){
        updateController.registerBot(this);
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
//        var originalMessage = update.getMessage();
//        log.debug(originalMessage.getText());
        updateController.processUpdate(update);
    }

    public void sendMessage(SendMessage message){
        if(message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e){
                log.error(e);
            }
        }
    }
}
