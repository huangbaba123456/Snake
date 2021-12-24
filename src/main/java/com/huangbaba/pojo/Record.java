package com.huangbaba.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther:huangbaba
 * @Date: 2021/12/10 - 12 - 10 - 11:35
 * @DEscription: com.huangbaba.pojo
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    String username;
    String type;
    int score;
}
