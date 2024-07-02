package com.ex.mobilestore.dto;

import lombok.Data;

import java.util.Random;

@Data
public class ProductDetailDto {
    private Integer id;
    private ProductDto productDto;
    private String itemCode;

    public ProductDetailDto(Integer id, ProductDto productDto) {
        this.id = id;
        this.productDto = productDto;
        this.itemCode = generateRandomNumericCode(9);
    }

    private String generateRandomNumericCode(int length){
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
