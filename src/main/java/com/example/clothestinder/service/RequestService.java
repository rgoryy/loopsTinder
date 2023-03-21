package com.example.clothestinder.service;

import com.example.clothestinder.dao.RequestDAO;
import com.example.clothestinder.dao.UserDAO;
import com.example.clothestinder.dto.AddRequestDTO;
import com.example.clothestinder.entity.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.clothestinder.entity.enums.Tonality.*;

@Service
public class RequestService {
    private final RequestDAO requestDAO;
    private final UserDAO userDAO;
    public RequestService(RequestDAO requestDAO, UserDAO userDAO) {
        this.requestDAO = requestDAO;
        this.userDAO = userDAO;
    }

    public void addNewRequest(AddRequestDTO addRequestDTO) {
        Request newRequest = Request.builder()
                .userId(userDAO.getUserByLogin(addRequestDTO.getLogin()).getId())
                .bpmMin(addRequestDTO.getBpmMin())
                .bpmMax(addRequestDTO.getBpmMax())
                .tonality(addRequestDTO.getTonality())
                .tags(addRequestDTO.getTags())
                .build();
        requestDAO.save(newRequest);
    }
}
