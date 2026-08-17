package com.autoprocess.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record TriggerWorkflowRequest(@NotNull(message = "事件载荷不能为空") Map<String, Object> payload) {
}
