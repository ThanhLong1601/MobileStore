package com.ex.mobilestore.utils;

import com.ex.mobilestore.constant.AppConstant;
import com.ex.mobilestore.dto.common.BaseResponse;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {
    private ResponseFactory() {}

    public static <T> ResponseEntity<BaseResponse<T>> ok(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setData(data);
        response.setStatus(AppConstant.SUCCESS_CODE);
        response.setDescription("success");

        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<BaseResponse<T>> error(T data, String errorCode, String msg) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setData(data);
        response.setStatus(errorCode);
        response.setDescription(msg);

        return ResponseEntity.status(Integer.parseInt(errorCode)).body(response);
    }
}
