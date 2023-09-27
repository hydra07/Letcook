/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.letcook.Util;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xuant
 */
public class UploadImg {

    public String upload(String fileName, String UPLOAD_DIR, String applicationPath, Part part) {
        String uploadPath = "E:\\java\\LetcookProject\\web" + File.separator + UPLOAD_DIR;
        String dbFileName = "";
        // Kiểm tra xem tệp có tồn tại trong thư mục upload không
        int fileNumber = 1;
        File fileToSave = new File(uploadPath, fileName);
        while (fileToSave.exists() && !fileToSave.isDirectory()) {
            // Tạo tên tệp mới bằng cách thêm số đằng sau tên tệp gốc
            String newFileName = fileName.replaceFirst("[.][^.]+$", "") + "_" + fileNumber + fileName.substring(fileName.lastIndexOf("."));
            fileNumber++;
            fileToSave = new File(uploadPath, newFileName);
        }
        fileName = fileToSave.getName();

        System.out.println("applicationPath:" + applicationPath);
        File fileUploadDirectory = new File(uploadPath);
        if (!fileUploadDirectory.exists()) {
            fileUploadDirectory.mkdirs();
        }
        String savePath = uploadPath + File.separator + fileName;
        System.out.println("savePath: " + savePath);
        String sRootPath = new File(savePath).getAbsolutePath();
        System.out.println("sRootPath: " + sRootPath);
        try {
            part.write(savePath + File.separator);
        } catch (IOException ex) {
            Logger.getLogger(UploadImg.class.getName()).log(Level.SEVERE, null, ex);
        }
        File fileSaveDir1 = new File(savePath);
        /*if you may have more than one files with same name then you can calculate some random characters
         and append that characters in fileName so that it will  make your each image name identical.*/
        dbFileName = UPLOAD_DIR + File.separator + fileName;
        try {
            part.write(savePath + File.separator);
        } catch (IOException ex) {
            Logger.getLogger(UploadImg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dbFileName;
    }

    public String extractFileName(Part part) {//This method will print the file name.
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
