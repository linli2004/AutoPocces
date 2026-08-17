package com.autoprocess.service;

import com.autoprocess.entity.NodeExecutionLog;
import com.autoprocess.entity.WorkflowInstance;

import java.util.List;

/**
 * Service contract for workflow runtime queries.
 */
public interface ExecutionQueryService {
    List<WorkflowInstance> listInstances();

    WorkflowInstance getInstance(String id);

    List<NodeExecutionLog> listLogs(String instanceId);
}
