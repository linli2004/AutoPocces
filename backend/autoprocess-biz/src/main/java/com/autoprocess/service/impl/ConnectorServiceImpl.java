package com.autoprocess.service.impl;

import com.autoprocess.common.exception.BusinessException;
import com.autoprocess.entity.Connector;
import com.autoprocess.entity.ConnectorAction;
import com.autoprocess.mapper.ConnectorActionMapper;
import com.autoprocess.mapper.ConnectorMapper;
import com.autoprocess.service.ConnectorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Connector service implementation.
 * Developers should complete real adapter invocation in executeAction.
 */
@Service
public class ConnectorServiceImpl implements ConnectorService {
    private final ConnectorMapper connectorMapper;
    private final ConnectorActionMapper connectorActionMapper;

    public ConnectorServiceImpl(ConnectorMapper connectorMapper, ConnectorActionMapper connectorActionMapper) {
        this.connectorMapper = connectorMapper;
        this.connectorActionMapper = connectorActionMapper;
    }

    @Override
    public List<Connector> listConnectors() {
        return connectorMapper.selectList();
    }

    @Override
    public List<ConnectorAction> listActions(String connectorId) {
        return connectorActionMapper.selectByConnectorId(connectorId);
    }

    @Override
    public Map<String, Object> testAction(String connectorId,String actionKey, Map<String, Object> input) {
        return executeAction(connectorId,actionKey, input);
    }

    @Override
    public Map<String, Object> executeAction(String connectorId,String actionKey, Map<String, Object> input) {
        throw new BusinessException("Connector action execution is not implemented yet: " + actionKey);
    }
}
