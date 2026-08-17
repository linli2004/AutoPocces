package com.autoprocess.service;

import com.autoprocess.entity.WorkflowDefinition;

import java.util.List;

/**
 * Service contract for workflow definition management.
 */
public interface WorkflowDefinitionService {
    List<WorkflowDefinition> list();

    WorkflowDefinition get(String id);

    WorkflowDefinition getByWorkflowKey(String workflowKey);

    WorkflowDefinition save(WorkflowDefinition workflow);

    WorkflowDefinition publish(String id);
}
