package com.ex.mobilestore.dto.common;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private String status;
    private String description;
    private T data;
}
