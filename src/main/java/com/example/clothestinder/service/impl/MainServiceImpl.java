package com.example.clothestinder.service.impl;

import com.example.clothestinder.dao.RawDataDAO;
import com.example.clothestinder.entity.RawData;
import com.example.clothestinder.service.MainService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MainServiceImpl implements MainService {
    private final RawDataDAO rawDataDAO;

    public MainServiceImpl(RawDataDAO rawDataDAO) {
        this.rawDataDAO = rawDataDAO;
    }

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);
//        var message = update.getMessage();
//        var sendMessage = new SendMessage();
//        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setText("Data");

    }

    private void saveRawData(Update update) {
        //pattern builder
        RawData rawData = RawData.builder()
                                 .update(update)
                                 .build();
        //spring created method "save" and its realization automatically
        rawDataDAO.save(rawData);
    }
}
