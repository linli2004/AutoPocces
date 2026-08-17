package com.autoprocess.engine;

import com.autoprocess.common.util.IdUtil;
import com.autoprocess.entity.ApprovalTask;
import com.autoprocess.entity.WorkflowNode;
import com.autoprocess.enums.ApprovalStatus;
import com.autoprocess.mapper.ApprovalTaskMapper;
import com.autoprocess.service.JsonDataService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Creates approval tasks and pauses the workflow instance.
 */
@Component
public class ApprovalNodeExecutor implements NodeExecutor {
    private final ApprovalTaskMapper approvalTaskMapper;
    private final JsonDataService jsonDataService;

    public ApprovalNodeExecutor(ApprovalTaskMapper approvalTaskMapper, JsonDataService jsonDataService) {
        this.approvalTaskMapper = approvalTaskMapper;
        this.jsonDataService = jsonDataService;
    }

    @Override
    public boolean supports(String nodeType) {
        return "APPROVAL".equals(nodeType);
    }

    @Override
    public NodeResult execute(WorkflowNode node, WorkflowRuntime runtime, Map<String, Object> context) {
        ApprovalTask task = new ApprovalTask(
                IdUtil.nextId(),
                runtime.instanceId(),
                node.id(),
                String.valueOf(node.config().getOrDefault("title", node.name())),
                String.valueOf(node.config().getOrDefault("assignee", "manager")),
                ApprovalStatus.PENDING,
                Map.copyOf(context),
                LocalDateTime.now(),
                null,
                null
        );
        jsonDataService.fillApprovalTaskJson(task);
        approvalTaskMapper.insert(task);
        return NodeResult.waiting(Map.of("taskId", task.id(), "assignee", task.assignee()));
    }
}
