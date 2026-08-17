package com.autoprocess.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

/**
 * 连接器动作，表示某个连接器可以暴露给流程画布调用的一项能力。
 */
@Entity
@Table(name = "connector_action")
public class ConnectorAction {
    @Id
    @Column(name = "id", length = 64)
    private String id;

    @Column(name = "connector_id", nullable = false, length = 64)
    private String connectorId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /** 动作标识，用于流程节点配置中引用该动作，例如 queryCustomer、updateOrderStatus。 */
    @Column(name = "action_key", nullable = false, length = 100)
    private String actionKey;

    /** 调用方法，例如 GET、POST、PUT、SQL_SELECT，用于决定动作执行方式。 */
    @Column(name = "method", nullable = false, length = 20)
    private String method;

    @Column(name = "path", nullable = false, length = 255)
    private String path;

    @Lob
    @Column(name = "input_schema", columnDefinition = "json")
    private String inputSchema;

    @Lob
    @Column(name = "output_schema", columnDefinition = "json")
    private String outputSchema;

    protected ConnectorAction() {
    }

    public ConnectorAction(String id, String connectorId, String name, String actionKey, String method, String path, String inputSchema, String outputSchema) {
        this.id = id;
        this.connectorId = connectorId;
        this.name = name;
        this.actionKey = actionKey;
        this.method = method;
        this.path = path;
        this.inputSchema = inputSchema;
        this.outputSchema = outputSchema;
    }

    public String id() { return id; }
    public String connectorId() { return connectorId; }
    public String name() { return name; }
    public String actionKey() { return actionKey; }
    public String method() { return method; }
    public String path() { return path; }
    public String inputSchema() { return inputSchema; }
    public String outputSchema() { return outputSchema; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getConnectorId() { return connectorId; }
    public void setConnectorId(String connectorId) { this.connectorId = connectorId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getActionKey() { return actionKey; }
    public void setActionKey(String actionKey) { this.actionKey = actionKey; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getInputSchema() { return inputSchema; }
    public void setInputSchema(String inputSchema) { this.inputSchema = inputSchema; }
    public String getOutputSchema() { return outputSchema; }
    public void setOutputSchema(String outputSchema) { this.outputSchema = outputSchema; }
}
