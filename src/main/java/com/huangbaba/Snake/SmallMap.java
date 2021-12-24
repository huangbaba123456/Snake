package com.huangbaba.Snake;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther:huangbaba
 * @Date: 2021/12/12 - 12 - 12 - 15:29
 * @DEscription: com.huangbaba.User
 * @version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmallMap {
    public boolean isH=false;
    //public HashMap<Integer,Food> foodHashMap;
    public List<Food> foodList=new LinkedList<>();
    //key:使用者的名字，div为头结点信息
    public HashMap<String,HeadDiv> divHashMap;
    //这个表示新加入的蛇，它的数据必须全部传给前段
    public HashMap<String,Snake> map_new;
    //public List<String> removeName;
    public Monster[] monsterLinkedList;
}
