package com.huangbaba.service.impl;

import com.huangbaba.mapper.RecordMapper;
import com.huangbaba.pojo.Record;
import com.huangbaba.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther:huangbaba
 * @Date: 2021/12/10 - 12 - 10 - 11:35
 * @DEscription: com.huangbaba.service.impl
 * @version: 1.0
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordMapper recordMapper;
    @Override
    public int insertRecord(Record record) {
        return recordMapper.insertRecord(record);
    }
    @Override
    public List<Record> selectRecord(String username) {
        return recordMapper.selectRecord(username);
    }
}
