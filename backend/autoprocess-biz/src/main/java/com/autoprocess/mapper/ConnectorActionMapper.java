package com.autoprocess.mapper;

import com.autoprocess.entity.ConnectorAction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access for actions exposed by a connector.
 */
@Mapper
@Repository
public interface ConnectorActionMapper {
    List<ConnectorAction> selectByConnectorId(@Param("connectorId") String connectorId);

    ConnectorAction selectByActionKeyAndConnector(@Param("connectorId") String connectorId,@Param("actionKey") String actionKey);
}
