package com.example.clothestinder.controller;

import com.example.clothestinder.dto.AddRequestDTO;
import com.example.clothestinder.dto.RegisterUserDTO;
import com.example.clothestinder.service.RequestService;
import com.example.clothestinder.service.TagService;
import com.example.clothestinder.service.UserService;
import com.example.clothestinder.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@Component
@Log4j
@RequestMapping("api")
public class UpdateController {
    private final UserService userService;
    private final TagService tagService;
    private final RequestService requestService;

    @Autowired
    public UpdateController(UserService userService, TagService tagService, RequestService requestService, MessageUtils messageUtils) {
        this.userService = userService;
        this.tagService = tagService;
        this.requestService = requestService;
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserDTO registerUserDTO){
        userService.addNewUser(registerUserDTO);
        return ResponseEntity.ok("");
    }

    @PostMapping("add-request")
    public ResponseEntity<?> processAddRequest(@RequestBody AddRequestDTO addRequestDTO) {
        requestService.addNewRequest(addRequestDTO);
        return ResponseEntity.ok("");
    }
}
