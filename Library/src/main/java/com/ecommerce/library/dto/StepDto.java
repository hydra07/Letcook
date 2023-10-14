package com.ecommerce.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StepDto {
    private String description;
    private List<MultipartFile> imgStep;
}
