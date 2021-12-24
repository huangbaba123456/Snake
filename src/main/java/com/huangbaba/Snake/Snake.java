package com.huangbaba.Snake;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * @Auther:huangbaba
 * @Date: 2021/12/9 - 12 - 09 - 9:31
 * @DEscription: com.huangbaba.User
 * @version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Snake {
    //当前玩家是否开启了磁铁 1代表没开，2 代表开了
    public int i=1;
    //嘎嘎嘎当前玩家的宽高比 width
    double radio;
    //当前是否跟新
    public boolean isV=false;
    //判断延迟次数
    public int delayCount=0;
    //有几个蛇没有接受到当前蛇的数据
    //也就是所当nameSet.size()==0时，这条蛇不是新加入的蛇
   public HashSet<String> nameSet=new HashSet<>();
    //每一个使用者一个
    //double widthBody;//屏幕宽度
    //double heightBody;//屏幕高度
    String username;//使用者的名字
    public int direct;//移动的方向
    LinkedList<Div> snakeBody;//蛇的身体
    boolean death=false;
    int count=1000;//判断是否掉线；
    //double divWidth;
    //double divHeight;
    public void g(){
        count=1000;
    }
    //public int addCount=0;
    public boolean speed=false;//是否加速
    public Snake(/*double widthBody,double heightBody,*/String username,int direct,double radio/*, double divWidth,double divHeight*/){
        snakeBody=new LinkedList<>();//new ArrayList<>();
//        this.widthBody=widthBody;
//        this.heightBody=heightBody;
        this.direct=direct;
        this.username=username;
        this.radio=radio;
//        this.divWidth=divWidth;
//        this.divHeight=divHeight;
    }
    public void add(){
        //当蛇吃到食物的时候调用这个方法
        Div div=snakeBody.get(snakeBody.size()-1).copy();
        snakeBody.add(div);
    }
}
