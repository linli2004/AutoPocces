package com.autoprocess.service;

import com.autoprocess.entity.EventRecord;
import com.autoprocess.entity.WorkflowInstance;

import java.util.List;
import java.util.Map;

/**
 * Service contract for manual triggers and external webhook events.
 */
public interface EventGatewayService {
    WorkflowInstance triggerWorkflow(String workflowId, Map<String, Object> payload);

    WorkflowInstance receiveWebhook(String workflowKey, Map<String, Object> payload);

    List<EventRecord> listEvents();
}
