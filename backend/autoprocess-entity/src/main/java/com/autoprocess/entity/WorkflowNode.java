package com.autoprocess.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

import java.util.Map;

/**
 * 流程节点模型，当前用于内存引擎执行，后续可序列化到 definition_json。
 */
@Embeddable
public class WorkflowNode {
    @Column(name = "node_id", length = 64)
    private String id;

    /** 节点类型，例如 START_EVENT、CONNECTOR_ACTION、IF、APPROVAL、EMAIL、END，用于匹配对应节点执行器。 */
    @Column(name = "node_type", length = 50)
    private String type;

    @Column(name = "node_name", length = 100)
    private String name;

    @Transient
    private Map<String, Object> config;

    protected WorkflowNode() {
    }

    public WorkflowNode(String id, String type, String name, Map<String, Object> config) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.config = config;
    }

    public String id() { return id; }
    public String type() { return type; }
    public String name() { return name; }
    public Map<String, Object> config() { return config; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Map<String, Object> getConfig() { return config; }
    public void setConfig(Map<String, Object> config) { this.config = config; }
}
