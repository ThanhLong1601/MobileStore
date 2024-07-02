package com.ex.mobilestore.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Integer id;
    private String productName;
    private String productImage;
    private String productDescription;
    private BigDecimal price;
    private ManufactureDto manufacture;
    private CategoryDto category;
    private Integer stockQuantity;
    @JsonIgnore
    private MultipartFile imageFile;
}
