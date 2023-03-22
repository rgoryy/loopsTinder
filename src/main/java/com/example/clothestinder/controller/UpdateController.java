package com.example.clothestinder.controller;

import com.example.clothestinder.dto.AddRequestDTO;
import com.example.clothestinder.dto.RegisterUserDTO;
import com.example.clothestinder.service.LoopStorageService;
import com.example.clothestinder.service.RequestService;
import com.example.clothestinder.service.TagService;
import com.example.clothestinder.service.UserService;
import com.example.clothestinder.utils.MessageUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;


@RestController
@Log4j
@RequestMapping("api")
public class UpdateController {
    private final UserService userService;
    private final TagService tagService;
    private final RequestService requestService;
    private final LoopStorageService filesStorageService;

    @Autowired
    public UpdateController(UserService userService, TagService tagService, RequestService requestService, MessageUtils messageUtils, LoopStorageService filesStorageService) {
        this.userService = userService;
        this.tagService = tagService;
        this.requestService = requestService;
        this.filesStorageService = filesStorageService;
    }

    @PostMapping("register")
    public void registerUser(@RequestBody RegisterUserDTO registerUserDTO){
        userService.addNewUser(registerUserDTO);
    }

    @PostMapping("add-request")
    public void addRequest(@RequestBody AddRequestDTO addRequestDTO) {
        requestService.addNewRequest(addRequestDTO);
    }

    @PostMapping("upload-file")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        filesStorageService.saveFile(file);
    }

    @GetMapping("download-file/{file}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("file") String fileName) throws MalformedURLException {
        Resource file = filesStorageService.loadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
