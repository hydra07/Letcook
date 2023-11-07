package com.ecommerce.admin.controller;

import com.ecommerce.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ImageUpload imageUpload;

    @GetMapping("/images/{path:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String path){
//        System.out.println("path:" + path);
        System.out.println("ok");
        String pathImage = imageUpload.IMAGES_FOLDER + path;

        pathImage = pathImage.replace("\\", "/");
        System.out.println("pathImage:" + pathImage);
        File imageFile = new File(pathImage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        if (imageFile.exists()) {
            try {
                return new ResponseEntity<>(Files.readAllBytes(imageFile.toPath()), headers, HttpStatus.OK);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/images/{path1}/{path2:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String path1,@PathVariable String path2){
//        System.out.println("path:" + path);
        System.out.println("ok");

        String pathImage = imageUpload.IMAGES_FOLDER + path1 + "\\" + path2;

        pathImage = pathImage.replace("\\", "/");
        System.out.println("pathImage:" + pathImage);
        File imageFile = new File(pathImage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        if (imageFile.exists()) {
            try {
                return new ResponseEntity<>(Files.readAllBytes(imageFile.toPath()), headers, HttpStatus.OK);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
