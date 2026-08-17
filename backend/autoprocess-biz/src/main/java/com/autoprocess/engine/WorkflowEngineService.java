package com.autoprocess.engine;

import com.autoprocess.entity.WorkflowInstance;

import java.util.Map;

/**
 * Service contract for workflow runtime execution.
 */
public interface WorkflowEngineService {
    WorkflowInstance start(String workflowId, Map<String, Object> payload);

    WorkflowInstance resumeAfterApproval(String instanceId, String approvedNodeId, boolean approved);
}
