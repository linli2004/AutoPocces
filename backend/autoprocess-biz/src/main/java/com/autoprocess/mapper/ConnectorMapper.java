package com.autoprocess.mapper;

import com.autoprocess.entity.Connector;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access for external system connectors.
 */
@Mapper
@Repository
public interface ConnectorMapper {
    List<Connector> selectList();

    Connector selectById(@Param("id") String id);
}
