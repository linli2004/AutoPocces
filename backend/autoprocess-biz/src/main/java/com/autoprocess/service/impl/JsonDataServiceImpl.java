package com.autoprocess.service.impl;

import com.autoprocess.common.exception.BusinessException;
import com.autoprocess.entity.ApprovalTask;
import com.autoprocess.entity.EventRecord;
import com.autoprocess.entity.NodeExecutionLog;
import com.autoprocess.entity.WorkflowDefinition;
import com.autoprocess.entity.WorkflowEdge;
import com.autoprocess.entity.WorkflowInstance;
import com.autoprocess.entity.WorkflowNode;
import com.autoprocess.service.JsonDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Converts JSON columns to Java fields before returning data to controllers.
 */
@Service
public class JsonDataServiceImpl implements JsonDataService {
    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {
    };
    private static final TypeReference<List<WorkflowNode>> NODE_LIST_TYPE = new TypeReference<>() {
    };
    private static final TypeReference<List<WorkflowEdge>> EDGE_LIST_TYPE = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;

    public JsonDataServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String toJson(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new BusinessException("JSON serialize failed: " + ex.getMessage());
        }
    }

    @Override
    public Map<String, Object> toMap(String json) {
        if (json == null || json.isBlank()) {
            return Map.of();
        }
        try {
            return objectMapper.readValue(json, MAP_TYPE);
        } catch (JsonProcessingException ex) {
            throw new BusinessException("JSON parse failed: " + ex.getMessage());
        }
    }

    @Override
    public void fillWorkflowDefinitionJson(WorkflowDefinition workflow) {
        workflow.setDefinitionJson(toJson(Map.of(
                "nodes", workflow.nodes() == null ? List.of() : workflow.nodes(),
                "edges", workflow.edges() == null ? List.of() : workflow.edges()
        )));
    }

    @Override
    public void parseWorkflowDefinitionJson(WorkflowDefinition workflow) {
        if (workflow == null || workflow.definitionJson() == null || workflow.definitionJson().isBlank()) {
            return;
        }
        try {
            JsonNode root = objectMapper.readTree(workflow.definitionJson());
            workflow.setNodes(objectMapper.convertValue(root.path("nodes"), NODE_LIST_TYPE));
            workflow.setEdges(objectMapper.convertValue(root.path("edges"), EDGE_LIST_TYPE));
        } catch (JsonProcessingException ex) {
            throw new BusinessException("Workflow definition JSON parse failed: " + ex.getMessage());
        }
    }

    @Override
    public void fillWorkflowInstanceJson(WorkflowInstance instance) {
        instance.setContextJson(toJson(instance.context()));
    }

    @Override
    public void parseWorkflowInstanceJson(WorkflowInstance instance) {
        if (instance != null) {
            instance.setContext(toMap(instance.contextJson()));
        }
    }

    @Override
    public void fillNodeLogJson(NodeExecutionLog log) {
        log.setInputJson(toJson(log.input()));
        log.setOutputJson(toJson(log.output()));
    }

    @Override
    public void parseNodeLogJson(NodeExecutionLog log) {
        if (log != null) {
            log.setInput(toMap(log.inputJson()));
            log.setOutput(toMap(log.outputJson()));
        }
    }

    @Override
    public void fillApprovalTaskJson(ApprovalTask task) {
        task.setFormDataJson(toJson(task.formData()));
    }

    @Override
    public void parseApprovalTaskJson(ApprovalTask task) {
        if (task != null) {
            task.setFormData(toMap(task.formDataJson()));
        }
    }

    @Override
    public void fillEventRecordJson(EventRecord eventRecord) {
        eventRecord.setPayloadJson(toJson(eventRecord.payload()));
    }

    @Override
    public void parseEventRecordJson(EventRecord eventRecord) {
        if (eventRecord != null) {
            eventRecord.setPayload(toMap(eventRecord.payloadJson()));
        }
    }
}
