package com.autoprocess.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record ActionTestRequest(@NotNull(message = "测试入参不能为空") Map<String, Object> input) {
}
