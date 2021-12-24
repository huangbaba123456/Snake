package com.huangbaba.service;

import com.huangbaba.pojo.Record;

import java.util.List;

/**
 * @Auther:huangbaba
 * @Date: 2021/12/10 - 12 - 10 - 11:34
 * @DEscription: com.huangbaba.service
 * @version: 1.0
 */
public interface RecordService {
    int insertRecord(Record record);
    List<Record> selectRecord(String username);
}
