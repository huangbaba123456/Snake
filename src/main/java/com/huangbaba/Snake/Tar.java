package com.huangbaba.Snake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther:huangbaba
 * @Date: 2021/11/18 - 11 - 18 - 17:53
 * @DEscription: com.huangbaba.pojo
 * @version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tar {
    private int value;
    private String name;
    private int direct;
    public String toString(){
        return " value = "+value+" name = "+name;
    }
}
