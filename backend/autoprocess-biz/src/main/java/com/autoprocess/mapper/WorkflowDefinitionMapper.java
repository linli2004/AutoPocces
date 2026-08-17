package com.autoprocess.mapper;

import com.autoprocess.entity.WorkflowDefinition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access for workflow definitions saved by the designer.
 */
@Mapper
@Repository
public interface WorkflowDefinitionMapper {
    List<WorkflowDefinition> selectList();

    WorkflowDefinition selectById(@Param("id") String id);

    WorkflowDefinition selectByWorkflowKey(@Param("workflowKey") String workflowKey);

    int insert(WorkflowDefinition workflowDefinition);

    int update(WorkflowDefinition workflowDefinition);

    int updateStatus(WorkflowDefinition workflowDefinition);
}
