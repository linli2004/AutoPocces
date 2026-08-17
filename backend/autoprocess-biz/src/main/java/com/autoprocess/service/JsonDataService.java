package com.autoprocess.service;

import com.autoprocess.entity.WorkflowDefinition;
import com.autoprocess.entity.WorkflowInstance;
import com.autoprocess.entity.NodeExecutionLog;
import com.autoprocess.entity.ApprovalTask;
import com.autoprocess.entity.EventRecord;

import java.util.Map;

/**
 * Service contract for JSON fields stored in MySQL.
 */
public interface JsonDataService {
    String toJson(Object value);

    Map<String, Object> toMap(String json);

    void fillWorkflowDefinitionJson(WorkflowDefinition workflow);

    void parseWorkflowDefinitionJson(WorkflowDefinition workflow);

    void fillWorkflowInstanceJson(WorkflowInstance instance);

    void parseWorkflowInstanceJson(WorkflowInstance instance);

    void fillNodeLogJson(NodeExecutionLog log);

    void parseNodeLogJson(NodeExecutionLog log);

    void fillApprovalTaskJson(ApprovalTask task);

    void parseApprovalTaskJson(ApprovalTask task);

    void fillEventRecordJson(EventRecord eventRecord);

    void parseEventRecordJson(EventRecord eventRecord);
}
