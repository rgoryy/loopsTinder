package com.example.clothestinder.service;

import com.example.clothestinder.dao.RequestDAO;
import com.example.clothestinder.entity.Request;
import com.example.clothestinder.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.clothestinder.entity.enums.Tonality.*;

@Service
public class RequestService {
    @Autowired
    private final RequestDAO requestDAO;

    public RequestService(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public void addNewRequest(User user) {
        Request request = Request.builder()
                .userId(user.getId())
                .bpmMin(100L)//todo изменить добавление
                .bpmMax(200L)//todo изменить добавление
                .tonality(EMINOR)//todo изменить добавление
                .build();
        requestDAO.save(request);
    }
}
