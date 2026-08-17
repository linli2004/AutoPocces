package com.autoprocess.controller;

import com.autoprocess.common.response.ApiResponse;
import com.autoprocess.dto.ApprovalHandleRequest;
import com.autoprocess.entity.ApprovalTask;
import com.autoprocess.entity.WorkflowInstance;
import com.autoprocess.enums.ApprovalStatus;
import com.autoprocess.service.ApprovalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 审批接口，提供待办查询、审批通过和审批拒绝操作。
 */
@RestController
@RequestMapping("/approval-tasks")
public class ApprovalController {
    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @GetMapping
    public ApiResponse<List<ApprovalTask>> list(@RequestParam(required = false) ApprovalStatus status) {
        return ApiResponse.ok(approvalService.list(status));
    }

    @GetMapping("/{id}")
    public ApiResponse<ApprovalTask> get(@PathVariable String id) {
        return ApiResponse.ok(approvalService.get(id));
    }

    @PostMapping("/{id}/approve")
    public ApiResponse<WorkflowInstance> approve(@PathVariable String id, @Valid @RequestBody ApprovalHandleRequest request) {
        return ApiResponse.ok(approvalService.approve(id, request.comment()));
    }

    @PostMapping("/{id}/reject")
    public ApiResponse<WorkflowInstance> reject(@PathVariable String id, @Valid @RequestBody ApprovalHandleRequest request) {
        return ApiResponse.ok(approvalService.reject(id, request.comment()));
    }
}
