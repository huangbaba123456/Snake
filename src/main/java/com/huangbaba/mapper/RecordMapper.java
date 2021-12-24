package com.huangbaba.mapper;

import com.huangbaba.pojo.Record;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Auther:huangbaba
 * @Date: 2021/12/10 - 12 - 10 - 11:36
 * @DEscription: com.huangbaba.mapper
 * @version: 1.0
 */
@Mapper
public interface RecordMapper {
    int insertRecord(Record record);
    List<Record> selectRecord(String username);
}
