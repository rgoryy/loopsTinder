package com.example.clothestinder.service;

import com.example.clothestinder.dao.LoopDAO;
import com.example.clothestinder.entity.Loop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

@Service
public class LoopStorageService {
    @Value("${upload.path}")
    private String uploadPath;
    private final LoopDAO loopDAO;

    public LoopStorageService(LoopDAO loopDAO) {
        this.loopDAO = loopDAO;
    }

    public void saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();
        String resultFilepath = uploadPath + "/" + resultFilename;
        file.transferTo(new File(resultFilepath));

        Loop loop = Loop.builder()
                .userID(1L) //todo изменить добавление
                .filePath(resultFilepath)
                .build();
        loopDAO.save(loop);
    }

    public Resource loadFile(String fileName) throws MalformedURLException { //actually filepath
        Loop loop = loopDAO.getLoopByFilePath(uploadPath + "/" + fileName); //todo
        File file = new File(loop.getFilePath());
        Resource resource = new UrlResource(file.toURI());
        if (resource.exists()) {
            return resource;
        }
        return null;
    }

}
