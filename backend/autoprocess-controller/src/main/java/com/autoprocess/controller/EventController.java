package com.autoprocess.controller;

import com.autoprocess.common.response.ApiResponse;
import com.autoprocess.entity.EventRecord;
import com.autoprocess.entity.WorkflowInstance;
import com.autoprocess.service.EventGatewayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 事件接口，用于查看事件记录和接收外部系统 Webhook。
 */
@RestController
@RequestMapping("/events")
public class EventController {
    private final EventGatewayService eventGatewayService;

    public EventController(EventGatewayService eventGatewayService) {
        this.eventGatewayService = eventGatewayService;
    }

    @GetMapping
    public ApiResponse<List<EventRecord>> listEvents() {
        return ApiResponse.ok(eventGatewayService.listEvents());
    }

    @PostMapping("/webhook/{workflowKey}")
    public ApiResponse<WorkflowInstance> webhook(@PathVariable String workflowKey, @RequestBody Map<String, Object> payload) {
        return ApiResponse.ok(eventGatewayService.receiveWebhook(workflowKey, payload));
    }
}
