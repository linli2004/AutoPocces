package com.autoprocess.mapper;

import com.autoprocess.entity.EventRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access for event gateway receiving records.
 */
@Mapper
@Repository
public interface EventRecordMapper {
    List<EventRecord> selectList();

    int insert(EventRecord eventRecord);
}
