package com.ecommerce.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUpload {
    private final String UPLOAD_FOLDER = "D:\\java\\springboot\\loda\\MyEcommerce-sb\\Admin\\src\\main\\resources\\static\\images\\";
    private final String UPLOAD_FOLDER_OTHER = "D:\\java\\springboot\\loda\\MyEcommerce-sb\\Customer\\src\\main\\resources\\static\\images\\";

    public boolean uploadImage(MultipartFile imageProduct ,String directory){
        boolean isUpload = false;
        try {
            Files.copy(imageProduct.getInputStream(), Paths.get(UPLOAD_FOLDER + directory
                    + File.separator + imageProduct.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

//            Files.copy(imageProduct.getInputStream(), Paths.get(UPLOAD_FOLDER_OTHER + directory
//                    + File.separator + imageProduct.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

            isUpload = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return  isUpload;
    }

    public boolean checkExisted(MultipartFile imageProduct, String directory){
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER + directory + "\\" + imageProduct.getOriginalFilename());
            isExisted = file.exists();
        }catch (Exception e){
            e.printStackTrace();
        }

        return isExisted;
    }
    }
