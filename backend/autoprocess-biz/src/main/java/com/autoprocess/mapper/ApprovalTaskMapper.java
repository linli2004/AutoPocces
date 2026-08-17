package com.autoprocess.mapper;

import com.autoprocess.entity.ApprovalTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access for human approval tasks.
 */
@Mapper
@Repository
public interface ApprovalTaskMapper {
    List<ApprovalTask> selectList();

    List<ApprovalTask> selectByStatus(@Param("status") String status);

    ApprovalTask selectById(@Param("id") String id);

    int insert(ApprovalTask approvalTask);

    int updateHandleResult(ApprovalTask approvalTask);
}
