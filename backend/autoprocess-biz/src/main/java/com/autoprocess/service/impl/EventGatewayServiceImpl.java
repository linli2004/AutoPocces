package com.autoprocess.service.impl;

import com.autoprocess.common.util.IdUtil;
import com.autoprocess.engine.WorkflowEngineService;
import com.autoprocess.entity.EventRecord;
import com.autoprocess.entity.WorkflowDefinition;
import com.autoprocess.entity.WorkflowInstance;
import com.autoprocess.mapper.EventRecordMapper;
import com.autoprocess.service.EventGatewayService;
import com.autoprocess.service.JsonDataService;
import com.autoprocess.service.WorkflowDefinitionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Default event gateway implementation backed by mapper data.
 */
@Service
public class EventGatewayServiceImpl implements EventGatewayService {
    private final EventRecordMapper eventRecordMapper;
    private final WorkflowDefinitionService workflowDefinitionService;
    private final WorkflowEngineService workflowEngineService;
    private final JsonDataService jsonDataService;

    public EventGatewayServiceImpl(EventRecordMapper eventRecordMapper, WorkflowDefinitionService workflowDefinitionService, WorkflowEngineService workflowEngineService, JsonDataService jsonDataService) {
        this.eventRecordMapper = eventRecordMapper;
        this.workflowDefinitionService = workflowDefinitionService;
        this.workflowEngineService = workflowEngineService;
        this.jsonDataService = jsonDataService;
    }

    @Override
    @Transactional
    public WorkflowInstance triggerWorkflow(String workflowId, Map<String, Object> payload) {
        saveEvent(workflowId, "manual_trigger", payload);
        return workflowEngineService.start(workflowId, payload);
    }

    @Override
    @Transactional
    public WorkflowInstance receiveWebhook(String workflowKey, Map<String, Object> payload) {
        WorkflowDefinition workflow = workflowDefinitionService.getByWorkflowKey(workflowKey);
        saveEvent(workflow.id(), workflowKey, payload);
        return workflowEngineService.start(workflow.id(), payload);
    }

    @Override
    public List<EventRecord> listEvents() {
        List<EventRecord> events = eventRecordMapper.selectList();
        events.forEach(jsonDataService::parseEventRecordJson);
        return events;
    }

    private void saveEvent(String workflowId, String eventKey, Map<String, Object> payload) {
        EventRecord eventRecord = new EventRecord(IdUtil.nextId(), workflowId, eventKey, payload, "RECEIVED", LocalDateTime.now());
        jsonDataService.fillEventRecordJson(eventRecord);
        eventRecordMapper.insert(eventRecord);
    }
}
