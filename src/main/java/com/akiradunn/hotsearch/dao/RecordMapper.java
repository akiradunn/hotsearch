package com.akiradunn.hotsearch.dao;

import com.akiradunn.hotsearch.pojo.Record;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Integer id);

    Record selectByTitle(String title);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);

    List<Record> selectAll();

    int batchInsert(@Param("recordList") List<Record> recordList);

}