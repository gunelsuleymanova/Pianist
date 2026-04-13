package com.example.pianist.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
/// haminsinin Json strukturu eyni oldugu ucun  lazimdir data deyisir bir

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) /// "success": true, "message": "Silindi", "data": null bunun qarsini almaq ucun
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok(String message, T data) {
        return ApiResponse.<T>builder().success(true).message(message).data(data).build();
    }

    public static <T> ApiResponse<T> ok(String message) {
        return ApiResponse.<T>builder().success(true).message(message).build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder().success(false).message(message).build();
    }
}