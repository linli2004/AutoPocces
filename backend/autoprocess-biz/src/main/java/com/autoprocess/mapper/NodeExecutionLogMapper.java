package com.autoprocess.mapper;

import com.autoprocess.entity.NodeExecutionLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access for node execution logs.
 */
@Mapper
@Repository
public interface NodeExecutionLogMapper {
    List<NodeExecutionLog> selectByInstanceId(@Param("instanceId") String instanceId);

    int insert(NodeExecutionLog nodeExecutionLog);
}
