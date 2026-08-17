package com.autoprocess.service;

import com.autoprocess.entity.Connector;
import com.autoprocess.entity.ConnectorAction;

import java.util.List;
import java.util.Map;

/**
 * Service contract for connector catalog and connector action execution.
 */
public interface ConnectorService {
    List<Connector> listConnectors();

    List<ConnectorAction> listActions(String connectorId);

    Map<String, Object> testAction(String connectorId, String actionKey, Map<String, Object> input);

    Map<String, Object> executeAction(String connectorId, String actionKey, Map<String, Object> input);
}
