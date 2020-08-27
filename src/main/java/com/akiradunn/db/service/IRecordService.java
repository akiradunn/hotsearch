package com.akiradunn.db.service;

import com.akiradunn.db.model.Record;

import java.util.List;

/**
 * @author akiradunn
 * @since 2020/8/27 17:58
 */
public interface IRecordService {

    public List<Record> selectAll();

    public int batchInsert(List<Record> recordList);
}
