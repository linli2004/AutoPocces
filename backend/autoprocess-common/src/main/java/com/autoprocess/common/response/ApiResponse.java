package com.autoprocess.common.response;

/**
 * 后端统一响应结构，前端请求拦截器依赖 code/message/data 这三个字段解析结果。
 */
public record ApiResponse<T>(int code, String message, T data) {
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(0, "success", data);
    }

    public static ApiResponse<Void> ok() {
        return new ApiResponse<>(0, "success", null);
    }

    public static ApiResponse<Void> fail(String message) {
        return new ApiResponse<>(500, message, null);
    }
}
