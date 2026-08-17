package com.autoprocess.controller;

import com.autoprocess.common.response.ApiResponse;
import com.autoprocess.dto.ActionTestRequest;
import com.autoprocess.entity.Connector;
import com.autoprocess.entity.ConnectorAction;
import com.autoprocess.service.ConnectorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 连接器接口，给前端连接器页面和流程节点配置提供数据。
 */
@RestController
@RequestMapping
public class ConnectorController {
    private final ConnectorService connectorService;

    public ConnectorController(ConnectorService connectorService) {
        this.connectorService = connectorService;
    }

    @GetMapping("/connectors")
    public ApiResponse<List<Connector>> listConnectors() {
        return ApiResponse.ok(connectorService.listConnectors());
    }

    @GetMapping("/connectors/{connectorId}/actions")
    public ApiResponse<List<ConnectorAction>> listActions(@PathVariable String connectorId) {
        return ApiResponse.ok(connectorService.listActions(connectorId));
    }

    @PostMapping("/connectors/{connectorId}/actions/{actionKey}/test")
    public ApiResponse<Map<String, Object>> testAction(@PathVariable String connectorId, @PathVariable String actionKey, @Valid @RequestBody ActionTestRequest request) {
        return ApiResponse.ok(connectorService.testAction(connectorId,actionKey, request.input()));
    }
}
