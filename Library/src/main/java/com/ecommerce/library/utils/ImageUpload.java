package com.ecommerce.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUpload {
    private final String UPLOAD_FOLDER = "D:\\FPT\\FALL_2023\\SWP\\final project\\Letcook\\Database\\images\\";
    private final String UPLOAD_FOLDER_OTHER = "D:\\FPT\\FALL_2023\\SWP\\final project\\Letcook\\Database\\";
    public final String IMAGES_FOLDER = "D:\\FPT\\FALL_2023\\SWP\\final project\\Letcook\\Database\\images\\";

//    private final String UPLOAD_FOLDER = "E:\\Letcook\\Admin\\src\\main\\resources\\static\\images\\";
//    private final String UPLOAD_FOLDER_OTHER = "E:\\Letcook\\Admin\\src\\main\\resources\\static\\";

    //    private final String UPLOAD_FOLDER = "E:\\Letcook\\Database\\images\\";
//
//    private final String UPLOAD_FOLDER_OTHER = "E:\\Letcook\\Database\\";
//    public final String IMAGES_FOLDER = "E:\\Letcook\\Database\\images\\";
    public String uploadImage(MultipartFile imageProduct, String directory) {
        String targetFileName = null;
        try {
            String originalFileName = imageProduct.getOriginalFilename();
            String baseName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

            targetFileName = originalFileName;
            String targetFilePath = UPLOAD_FOLDER + directory + File.separator + targetFileName;

            // Kiểm tra xem tệp đã tồn tại chưa
            File targetFile = new File(targetFilePath);
            int fileNumber = 1;

            while (targetFile.exists()) {
                // Nếu tệp đã tồn tại, thêm số đằng sau trước phần mở rộng (extension)
                targetFileName = baseName + "_" + fileNumber + extension;
                targetFilePath = UPLOAD_FOLDER + directory + File.separator + targetFileName;
                targetFile = new File(targetFilePath);
                fileNumber++;
            }

            // Sao chép tệp vào thư mục đích với tên tệp đã điều chỉnh
            Files.copy(imageProduct.getInputStream(), Paths.get(targetFilePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetFileName;
    }


    public boolean checkExisted(MultipartFile imageProduct, String directory) {
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER + directory + "\\" + imageProduct.getOriginalFilename());
            isExisted = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isExisted;
    }

    public void deleteImage(String imagePath) {
        try {
            File fileToDelete = new File(UPLOAD_FOLDER_OTHER + imagePath);

            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    System.out.println("Deleted the file: " + imagePath);
                } else {
                    System.err.println("Failed to delete the file: " + imagePath);
                }
            } else {
                System.err.println("File does not exist: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while deleting the file: " + imagePath);
        }
    }

    public String getURL(String targetFileName, String directory) {
        return UPLOAD_FOLDER + directory + File.separator + targetFileName;
    }

}
