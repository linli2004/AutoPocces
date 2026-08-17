package com.autoprocess.dto;

import jakarta.validation.constraints.Size;

public record ApprovalHandleRequest(@Size(max = 500, message = "审批意见不能超过500字") String comment) {
}
