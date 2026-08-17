package com.autoprocess.mapper;

import com.autoprocess.entity.WorkflowInstance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access for workflow runtime instances.
 */
@Mapper
@Repository
public interface WorkflowInstanceMapper {
    List<WorkflowInstance> selectList();

    WorkflowInstance selectById(@Param("id") String id);

    int insert(WorkflowInstance workflowInstance);

    int updateRuntimeState(WorkflowInstance workflowInstance);
}
