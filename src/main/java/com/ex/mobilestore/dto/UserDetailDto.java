package com.ex.mobilestore.dto;

import lombok.Data;

@Data
public class UserDetailDto {
    private Integer id;
    private String fullName;
    private String email;
    private String address;
    private String phone;
}
