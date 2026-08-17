package com.autoprocess.controller;

import com.autoprocess.common.response.ApiResponse;
import com.autoprocess.entity.NodeExecutionLog;
import com.autoprocess.entity.WorkflowInstance;
import com.autoprocess.service.ExecutionQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 流程实例接口，给前端运行监控页面提供实例和节点日志。
 */
@RestController
@RequestMapping("/workflow-instances")
public class ExecutionController {
    private final ExecutionQueryService executionQueryService;

    public ExecutionController(ExecutionQueryService executionQueryService) {
        this.executionQueryService = executionQueryService;
    }

    @GetMapping
    public ApiResponse<List<WorkflowInstance>> listInstances() {
        return ApiResponse.ok(executionQueryService.listInstances());
    }

    @GetMapping("/{id}")
    public ApiResponse<WorkflowInstance> getInstance(@PathVariable String id) {
        return ApiResponse.ok(executionQueryService.getInstance(id));
    }

    @GetMapping("/{id}/node-logs")
    public ApiResponse<List<NodeExecutionLog>> listLogs(@PathVariable String id) {
        return ApiResponse.ok(executionQueryService.listLogs(id));
    }
}
