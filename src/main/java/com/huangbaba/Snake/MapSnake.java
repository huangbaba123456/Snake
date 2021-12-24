package com.huangbaba.Snake;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
/**
 * @Auther:huangbaba
 * @Date: 2021/12/9 - 12 - 09 - 8:40
 * @DEscription: com.huangbaba.pojo
 * @version: 1.0
 */
public class MapSnake {
    //所有的食物集合
    //public ArrayList<Food> foodList=new ArrayList<>();
    // public HashMap<Integer,Food> foodHashMap=new HashMap<>();
    public List<Food> foodList=new LinkedList<>();
    public HashMap<String,Snake> hashMap=new HashMap<>();
    public boolean isEnd=false;//是否结束游戏
    public int count=0;//此时游戏中的人数
    public Monster[] monsterLinkedList=new Monster[2];
    //public List<String> removeName=new ArrayList<>();//已近死亡或者退出的玩家;
}
