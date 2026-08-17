package com.autoprocess.controller;

import com.autoprocess.common.response.ApiResponse;
import com.autoprocess.dto.TriggerWorkflowRequest;
import com.autoprocess.entity.WorkflowDefinition;
import com.autoprocess.entity.WorkflowInstance;
import com.autoprocess.service.EventGatewayService;
import com.autoprocess.service.WorkflowDefinitionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 流程接口，提供流程定义查询、发布和手动触发能力。
 */
@RestController
@RequestMapping("/workflows")
public class WorkflowController {
    private final WorkflowDefinitionService workflowDefinitionService;
    private final EventGatewayService eventGatewayService;

    public WorkflowController(WorkflowDefinitionService workflowDefinitionService, EventGatewayService eventGatewayService) {
        this.workflowDefinitionService = workflowDefinitionService;
        this.eventGatewayService = eventGatewayService;
    }

    @GetMapping
    public ApiResponse<List<WorkflowDefinition>> list() {
        return ApiResponse.ok(workflowDefinitionService.list());
    }

    @GetMapping("/{id}")
    public ApiResponse<WorkflowDefinition> get(@PathVariable String id) {
        return ApiResponse.ok(workflowDefinitionService.get(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<WorkflowDefinition> save(@PathVariable String id, @RequestBody WorkflowDefinition workflow) {
        return ApiResponse.ok(workflowDefinitionService.save(workflow));
    }

    @PostMapping("/{id}/publish")
    public ApiResponse<WorkflowDefinition> publish(@PathVariable String id) {
        return ApiResponse.ok(workflowDefinitionService.publish(id));
    }

    @PostMapping("/{id}/trigger")
    public ApiResponse<WorkflowInstance> trigger(@PathVariable String id, @Valid @RequestBody TriggerWorkflowRequest request) {
        return ApiResponse.ok(eventGatewayService.triggerWorkflow(id, request.payload()));
    }

    @GetMapping("/node-types")
    public ApiResponse<List<Map<String, Object>>> nodeTypes() {
        return ApiResponse.ok(List.of(
                Map.of("type", "START_EVENT", "name", "新订单", "source", "事件网关"),
                Map.of("type", "CONNECTOR_ACTION", "name", "连接器动作", "source", "OpenAPI/数据库/HTTP连接器"),
                Map.of("type", "IF", "name", "条件判断", "source", "平台内置"),
                Map.of("type", "APPROVAL", "name", "人工审批", "source", "平台内置"),
                Map.of("type", "EMAIL", "name", "发送邮件", "source", "邮件连接器"),
                Map.of("type", "END", "name", "结束", "source", "平台内置")
        ));
    }
}
