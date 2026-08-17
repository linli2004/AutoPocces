package com.autoprocess.service;

import com.autoprocess.entity.ApprovalTask;
import com.autoprocess.entity.WorkflowInstance;
import com.autoprocess.enums.ApprovalStatus;

import java.util.List;

/**
 * Service contract for human approval tasks.
 */
public interface ApprovalService {
    List<ApprovalTask> list(ApprovalStatus status);

    ApprovalTask get(String id);

    WorkflowInstance approve(String id, String comment);

    WorkflowInstance reject(String id, String comment);
}
