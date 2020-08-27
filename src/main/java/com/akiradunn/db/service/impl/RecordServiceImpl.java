package com.akiradunn.db.service.impl;

import com.akiradunn.db.mapper.RecordMapper;
import com.akiradunn.db.model.Record;
import com.akiradunn.db.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author duanzengliang
 * @since 2020/8/27 17:59
 */
@Service
public class RecordServiceImpl implements IRecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<Record> selectAll() {
        return recordMapper.selectAll();
    }

    @Override
    public int batchInsert(List<Record> recordList) {
        return recordMapper.batchInsert(recordList);
    }
}
