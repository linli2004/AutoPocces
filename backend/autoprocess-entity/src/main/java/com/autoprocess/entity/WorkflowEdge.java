package com.autoprocess.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * 流程连线模型，用于描述节点之间的流转关系和分支条件。
 */
@Embeddable
public class WorkflowEdge {
    @Column(name = "edge_id", length = 64)
    private String id;

    @Column(name = "source_node_id", length = 64)
    private String source;

    @Column(name = "target_node_id", length = 64)
    private String target;

    @Column(name = "condition_expression", length = 100)
    private String condition;

    protected WorkflowEdge() {
    }

    public WorkflowEdge(String id, String source, String target, String condition) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.condition = condition;
    }

    public String id() { return id; }
    public String source() { return source; }
    public String target() { return target; }
    public String condition() { return condition; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
}
