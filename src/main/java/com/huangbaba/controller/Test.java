package com.huangbaba.controller;
import com.google.gson.Gson;
import com.huangbaba.Snake.*;
import com.huangbaba.pojo.Record;
import com.huangbaba.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @Auther:huangbaba
 * @Date: 2021/11/20 - 11 - 20 - 11:09
 * @DEscription: com.huangbaba.controller
 * @version: 1.0
 */
@Component
@Controller
//                                    http://localhost:8080/HuangbabaSnake/test.do
public class Test implements ApplicationRunner {
    final double dis = 0.65;
    final double bombSpeed=1;
    HashMap<String, Move> hashMap = new HashMap<>();
    ArrayList<Move> list = new ArrayList<>();
    Gson gson = new Gson();
    MapSnake mapSnake = new MapSnake();
    List<String> removeName=new ArrayList<>();
    //Angle foodAngle=new Angle();
    int ccc = 0;
    int hh=1000;
    int maxCount=5;
    int sizes=2;
    int time=100;
    //食物移动的速度
    double foodMove=0.5;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!mapSnake.isEnd) {
            //mapSnake=new MapSnake();
            timet();
        } else {
            System.out.println("重新开始");
            mapSnake = new MapSnake();
        }
    }
    public void timet() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                HashMap<String, Snake> hashMap = mapSnake.hashMap;
                //HashMap<Integer, String> death = new HashMap<>();
//                System.out.println("煌爸爸流弊");
//                System.out.println(hashMap);
                if (hashMap.size() <= 0) {
                    mapSnake.isEnd = true;
                    hh--;
                }else {
                    hh=1000;
                    mapSnake.isEnd=false;
                }
                if(mapSnake.isEnd==true&&hh==0){
                    System.out.println("重新开始");
                    //跟新房间数据
                    mapSnake=new MapSnake();
                    //foodAngle=new Angle();
                    hh=100;
                    return;
                }
                //遍历每一个蛇
                for (Map.Entry<String, Snake> entry : hashMap.entrySet()) {
                    Snake curSnake = entry.getValue();
                    curSnake.setCount(curSnake.getCount() -1);
                    String userName = entry.getKey();
                    if (curSnake.isDeath()) {
                        //煞笔蛇已经死亡
                        //数据存档
                        //*************//
                        removeName.add(userName);
                        continue;
                    }
//                    System.out.println(curSnake);
                    if(curSnake.getCount()<=0){
                        //掉线了
                        System.out.println(userName+"掉线了");
                        //调用存档功能
                        Record record=new Record();
                        record.setUsername(userName);
                        record.setType("连机");
                        record.setScore(curSnake.getCount());
                        recordService.insertRecord(record);
                        removeName.add(userName);
                        continue;
                    }
                    LinkedList<Div> snakeBody = null;
                    double setTarLeft;
                    double setTarTop;
                    double left = 0, top = 0;
                    boolean isfirst;
//                    double screenWidth = curSnake.getWidthBody();
//                    double screenHeight = curSnake.getHeightBody();
                    //int count = 0;
                    double beishu=1;
                    double headX;
                    double headY;
                    int addDivCount=0;
                    if(curSnake.speed){
                        beishu=2;
//                        if(curSnake.getSnakeBody().size()>1){
//                            curSnake.getSnakeBody().removeLast();
//                        }
                    }
                    //已经被跟新过了
                    //curSnake.isV=true;
                    curSnake.delayCount++;
                    switch (curSnake.direct) {
                        case 1:
                            //向上移动
                            snakeBody = curSnake.getSnakeBody();
                            //得到百分制下标
                            left = snakeBody.get(0).getTarLeft();
                            top = snakeBody.get(0).getTarTop();
                            headX=left+1;
                            headY=top+1;
                            if(headX>30&&headX<33&&headY>15&&headY<18){
                                //穿越隧道了
                                curSnake.getSnakeBody().get(0).setTarLeft(67.5);
                                curSnake.getSnakeBody().get(0).setTarTop(81.5);
                            }else {
                                if(headX>67&&headX<70&&headY>82&&headY<85){
                                    curSnake.getSnakeBody().get(0).setTarLeft(30.5);
                                    curSnake.getSnakeBody().get(0).setTarTop(15);
                                }else {
                                    if(headX>67&&headX<70&&headY>15&&headY<18){
                                        curSnake.getSnakeBody().get(0).setTarLeft(30.5);
                                        curSnake.getSnakeBody().get(0).setTarTop(81.5);
                                    }else {
                                        if(headX>30&&headX<33&&headY>82&&headY<85){
                                            //穿越隧道了
                                            curSnake.getSnakeBody().get(0).setTarLeft(67.5);
                                            curSnake.getSnakeBody().get(0).setTarTop(14.5);
                                        }else {
                                            if(headX>43.5&&headX<55.5&&headY>10&&headY<16){
                                                removeName.add(userName);
                                                Div deathHead=curSnake.getSnakeBody().get(0);
                                                Food food=new Food(deathHead.getTarLeft(),deathHead.getTarTop(),1);
                                                double rand=Math.random()*360;
                                                //换算成弧度制度
                                                food.setAngle((int) rand);
                                                mapSnake.foodList.add(food);
                                                break;
                                            }else {
                                                if(headX>43.5&&headX<55.5&&headY<93&&headY>87){
                                                    removeName.add(userName);
                                                    Div deathHead=curSnake.getSnakeBody().get(0);
                                                    Food food=new Food(deathHead.getTarLeft(),deathHead.getTarTop(),1);
                                                    double rand=Math.random()*360;
                                                    //换算成弧度制度
                                                    food.setAngle((int) rand);
                                                    mapSnake.foodList.add(food);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            setTarTop = curSnake.getSnakeBody().get(0).getTarTop() -dis*beishu*curSnake.getRadio();
                            if (setTarTop <= 0) {
                                //防止越界
                                setTarTop = 100;
                            }
                            //curSnake.getSnakeBody().get(0).setTarTop(setTarTop);
                            //整个身体一起移动
                            left = snakeBody.get(0).getTarLeft();
                            top = snakeBody.get(0).getTarTop();
                            addDivCount=eatFood(left,top,mapSnake.foodList,curSnake.getI()==2);
                            while (addDivCount>0){
                                snakeBody.add(snakeBody.getLast().copy());
                                addDivCount--;
                            }
                            snakeBody.get(0).setTarTop(setTarTop);
                            isfirst = false;
                            for (Div div : snakeBody) {
                                if (!isfirst) {
                                    //int indexX = (int) (100 * div.getTarLeft() / screenWidth+1);
                                    //int indexY = (int) (100 * div.getTarTop() / screenHeight+1);
                                    //death.put(getHash(indexX,indexY), userName);
                                    isfirst = true;
                                } else {
                                    double temp1 = div.getTarLeft();
                                    double temp2 = div.getTarTop();
                                    div.setTarTop(top);
                                    div.setTarLeft(left);
                                    //int indexX = (int) (100 * left / screenWidth+1);
                                    //int indexY = (int) (100 * top / screenHeight+1);
                                    //death.put(getHash(indexX,indexY), userName);
                                    top = temp2;
                                    left = temp1;
                                }
                            }
                            //count = eatFood(snakeBody.get(0).getTarLeft(), snakeBody.get(0).getTarTop(), screenWidth, screenHeight, mapSnake.foodHashMap);
                            //     curSnake.addCount+=count;
//                            while (count > 0) {
//                                for (int hh=0;hh<sizes;hh++){
//                                    snakeBody.add(snakeBody.get(snakeBody.size() - 1).copy());
//                                }
//                                count--;
//                            }
                            break;
                        case 2:
                            //向右移动
                            snakeBody = curSnake.getSnakeBody();
                            left = snakeBody.get(0).getTarLeft();
                            top = snakeBody.get(0).getTarTop();
                            headX=left+1;
                            headY=top+1;
                            if(headX>30&&headX<33&&headY>15&&headY<18){
                                //穿越隧道了
                                curSnake.getSnakeBody().get(0).setTarLeft(68.8);
                                curSnake.getSnakeBody().get(0).setTarTop(82.5);
                            }else {
                                if(headX>67&&headX<70&&headY>82&&headY<85){
                                    curSnake.getSnakeBody().get(0).setTarLeft(31.5);
                                    curSnake.getSnakeBody().get(0).setTarTop(15.5);
                                }else {
                                    if(headX>67&&headX<70&&headY>15&&headY<18){
                                        curSnake.getSnakeBody().get(0).setTarLeft(31.5);
                                        curSnake.getSnakeBody().get(0).setTarTop(82.5);
                                    }else {
                                        if(headX>30&&headX<33&&headY>82&&headY<85){
                                            //穿越隧道了
                                            curSnake.getSnakeBody().get(0).setTarLeft(68.8);
                                            curSnake.getSnakeBody().get(0).setTarTop(16.5);
                                        }else {
                                            if(headX>43.5&&headX<55.5&&headY>10&&headY<16){
                                                removeName.add(userName);
                                                Div deathHead=curSnake.getSnakeBody().get(0);
                                                Food food=new Food(deathHead.getTarLeft(),deathHead.getTarTop(),1);
                                                double rand=Math.random()*360;
                                                //换算成弧度制度
                                                food.setAngle((int) rand);
                                                mapSnake.foodList.add(food);
                                                break;
                                            }else {
                                                if(headX>43.5&&headX<55.5&&headY<93&&headY>87){
                                                    removeName.add(userName);
                                                    Div deathHead=curSnake.getSnakeBody().get(0);
                                                    Food food=new Food(deathHead.getTarLeft(),deathHead.getTarTop(),1);
                                                    double rand=Math.random()*360;
                                                    //换算成弧度制度
                                                    food.setAngle((int) rand);
                                                    mapSnake.foodList.add(food);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            left = snakeBody.get(0).getTarLeft();
                            top = snakeBody.get(0).getTarTop();
                            setTarLeft = curSnake.getSnakeBody().get(0).getTarLeft() +  dis*beishu;
                            if (setTarLeft >= 100) {
                                setTarLeft = 0;
                            }
                            addDivCount=eatFood(left,top,mapSnake.foodList,curSnake.getI()==2);
                            while (addDivCount>0){
                                snakeBody.add(snakeBody.getLast().copy());
                                addDivCount--;
                            }
                            snakeBody.get(0).setTarLeft(setTarLeft);
                            isfirst = false;
                            //跟新身体的坐标
                            for (Div div : snakeBody) {
                                if (!isfirst) {
//                                    int indexX = (int) (100 * div.getTarLeft() / screenWidth+1);
//                                    int indexY = (int) (100 * div.getTarTop() / screenHeight+1);
//                                    death.put(getHash(indexX,indexY), userName);
                                    isfirst = true;
                                } else {
                                    double temp1 = div.getTarLeft();
                                    double temp2 = div.getTarTop();
                                    div.setTarTop(top);
                                    div.setTarLeft(left);
//                                    int indexX = (int) (100 * left / screenWidth+1);
//                                    int indexY = (int) (100 * top / screenHeight+1);
//                                    death.put(getHash(indexX,indexY), userName);
                                    top = temp2;
                                    left = temp1;
                                }
                            }
                            //count = eatFood(snakeBody.get(0).getTarLeft(), snakeBody.get(0).getTarTop(), screenWidth, screenHeight, mapSnake.foodHashMap);
                            //     curSnake.addCount+=count;
                            break;
                        case 3:
                            //向下移动
                            snakeBody = curSnake.getSnakeBody();
                            left = snakeBody.get(0).getTarLeft();
                            top = snakeBody.get(0).getTarTop();
                            headX=left+1;
                            headY=top+1;
                            if(headX>30&&headX<33&&headY>15&&headY<18){
                                //穿越隧道了
                                curSnake.getSnakeBody().get(0).setTarLeft(67.5);
                                curSnake.getSnakeBody().get(0).setTarTop(85);
                            }else {
                                if(headX>67&&headX<70&&headY>82&&headY<85){
                                    curSnake.getSnakeBody().get(0).setTarLeft(30.5);
                                    curSnake.getSnakeBody().get(0).setTarTop(17);
                                }else {
                                    if(headX>67&&headX<70&&headY>15&&headY<18){
                                        curSnake.getSnakeBody().get(0).setTarLeft(30.5);
                                        curSnake.getSnakeBody().get(0).setTarTop(84);
                                    }else {
                                        if(headX>30&&headX<33&&headY>82&&headY<85){
                                            //穿越隧道了
                                            curSnake.getSnakeBody().get(0).setTarLeft(67.5);
                                            curSnake.getSnakeBody().get(0).setTarTop(17.5);
                                        }else {
                                            if(headX>43.5&&headX<55.5&&headY>10&&headY<16){
                                                removeName.add(userName);
                                                Div deathHead=curSnake.getSnakeBody().get(0);
                                                Food food=new Food(deathHead.getTarLeft(),deathHead.getTarTop(),1);
                                                double rand=Math.random()*360;
                                                //换算成弧度制度
                                                food.setAngle((int) rand);
                                                mapSnake.foodList.add(food);
                                                break;
                                            }else {
                                                if(headX>43.5&&headX<55.5&&headY<93&&headY>87){
                                                    removeName.add(userName);
                                                    Div deathHead=curSnake.getSnakeBody().get(0);
                                                    Food food=new Food(deathHead.getTarLeft(),deathHead.getTarTop(),1);
                                                    double rand=Math.random()*360;
                                                    //换算成弧度制度
                                                    food.setAngle((int) rand);
                                                    mapSnake.foodList.add(food);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            setTarTop = curSnake.getSnakeBody().get(0).getTarTop() +  dis*beishu*curSnake.getRadio();
                            if (setTarTop >= 100) {
                                setTarTop = 0;
                            }
                            left = snakeBody.get(0).getTarLeft();
                            top = snakeBody.get(0).getTarTop();
                            addDivCount=eatFood(left,top,mapSnake.foodList,curSnake.getI()==2);
                            while (addDivCount>0){
                                snakeBody.add(snakeBody.getLast().copy());
                                addDivCount--;
                            }
                            snakeBody.get(0).setTarTop(setTarTop);
                            isfirst = false;
                            for (Div div : snakeBody) {
                                if (!isfirst) {
//                                    int indexX = (int) (100 * div.getTarLeft() / screenWidth+1);
//                                    int indexY = (int) (100 * div.getTarTop() / screenHeight+1);
//                                    death.put(getHash(indexX,indexY), userName);
                                    isfirst = true;
                                } else {
                                    double temp1 = div.getTarLeft();
                                    double temp2 = div.getTarTop();
                                    div.setTarTop(top);
                                    div.setTarLeft(left);
//                                    int indexX = (int) (100 * left / screenWidth+1);
//                                    int indexY = (int) (100 * top / screenHeight+1);
//                                    death.put(getHash(indexX,indexY), userName);
                                    top = temp2;
                                    left = temp1;
                                }
                            }
                            //count = eatFood(snakeBody.get(0).getTarLeft(), snakeBody.get(0).getTarTop(), screenWidth, screenHeight, mapSnake.foodHashMap);
                            //   curSnake.addCount+=count;
                            break;
                        case 4:
                            //向左移动
                            snakeBody = curSnake.getSnakeBody();
                            left = snakeBody.get(0).getTarLeft();
                            top = snakeBody.get(0).getTarTop();
                            headX=left+1;
                            headY=top+1;
                            if(headX>30&&headX<33&&headY>15&&headY<18){
                                //穿越隧道了
                                curSnake.getSnakeBody().get(0).setTarLeft(66.5);
                                curSnake.getSnakeBody().get(0).setTarTop(82.5);
                            }else {
                                if(headX>67&&headX<70&&headY>82&&headY<85){
                                    curSnake.getSnakeBody().get(0).setTarLeft(29.5);
                                    curSnake.getSnakeBody().get(0).setTarTop(15.5);
                                }else {
                                    if(headX>67&&headX<70&&headY>15&&headY<18){
                                        curSnake.getSnakeBody().get(0).setTarLeft(29.5);
                                        curSnake.getSnakeBody().get(0).setTarTop(82.5);
                                    }else {
                                        if(headX>30&&headX<33&&headY>82&&headY<85){
                                            //穿越隧道了
                                            curSnake.getSnakeBody().get(0).setTarLeft(66.5);
                                            curSnake.getSnakeBody().get(0).setTarTop(15.5);
                                        }else {
                                            if(headX>43.5&&headX<55.5&&headY>10&&headY<16){
                                                removeName.add(userName);
                                                Div deathHead=curSnake.getSnakeBody().get(0);
                                                Food food=new Food(deathHead.getTarLeft(),deathHead.getTarTop(),1);
                                                double rand=Math.random()*360;
                                                //换算成弧度制度
                                                food.setAngle((int) rand);
                                                mapSnake.foodList.add(food);
                                                break;
                                            }else {
                                                if(headX>43.5&&headX<55.5&&headY<93&&headY>87){
                                                    removeName.add(userName);
                                                    Div deathHead=curSnake.getSnakeBody().get(0);
                                                    Food food=new Food(deathHead.getTarLeft(),deathHead.getTarTop(),1);
                                                    double rand=Math.random()*360;
                                                    //换算成弧度制度
                                                    food.setAngle((int) rand);
                                                    mapSnake.foodList.add(food);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            setTarLeft = curSnake.getSnakeBody().get(0).getTarLeft() - dis*beishu;
                            if (setTarLeft <= 0) {
                                setTarLeft = 100;
                            }
                            left = snakeBody.get(0).getTarLeft();
                            top = snakeBody.get(0).getTarTop();
                            addDivCount=eatFood(left,top,mapSnake.foodList,curSnake.getI()==2);
                            while (addDivCount>0){
                                snakeBody.add(snakeBody.getLast().copy());
                                addDivCount--;
                            }
                            snakeBody.get(0).setTarLeft(setTarLeft);
                            isfirst = false;
                            for (Div div : snakeBody) {
                                if (!isfirst) {
//                                    int indexX = (int) (100 * div.getTarLeft() / screenWidth+1);
//                                    int indexY = (int) (100 * div.getTarTop() / screenHeight+1);
//                                    death.put(getHash(indexX,indexY), userName);
                                    isfirst = true;
                                } else {
                                    double temp1 = div.getTarLeft();
                                    double temp2 = div.getTarTop();
                                    div.setTarTop(top);
                                    div.setTarLeft(left);
//                                    int indexX = (int) (100 * left / screenWidth+1);
//                                    int indexY = (int) (100 * top / screenHeight+1);
//                                    death.put(getHash(indexX,indexY), userName);
                                    top = temp2;
                                    left = temp1;
                                }
                            }
                            //count = eatFood(snakeBody.get(0).getTarLeft(), snakeBody.get(0).getTarTop(), screenWidth, screenHeight, mapSnake.foodHashMap);
                            //    curSnake.addCount+=count;
                            break;
                    }
                }
                //IfDeath(death);
                //然后我们再生成食物,生成食物的量和玩家的人数成正比
                int size = mapSnake.hashMap.size();
                //进行模特卡洛算法 size次
                int foodCount=mapSnake.foodList.size();
                while (foodCount<5){
                    Food food = addFood();
                    //mapSnake.foodHashMap.put(getHash(food.getX(), food.getY()), food);
                    //生成 1——360的随机数
                    double rand=Math.random()*360;
                    //换算成弧度制度
                    food.setAngle((int) rand);
                    //foodAngle.foodAngleHashMap.put(getHash((int)food.getX(),(int)food.getY())+food.getId(),an);
                    mapSnake.foodList.add(food);
                    foodCount++;
                }
                //  System.out.println(foodAngle.foodAngleHashMap);
//                if(foodCount<=5) {
//                    for (int h = 0; h < size * 5; h++) {
//                        if (isadd()) {
//                            Food food = addFood();
//                            //mapSnake.foodHashMap.put(getHash(food.getX(), food.getY()), food);
//                            //生成 1——360的随机数
//                            double rand=Math.random()*360;
//                            //换算成弧度制度
//                            food.setAngle((int) rand);
//                            //foodAngle.foodAngleHashMap.put(getHash((int)food.getX(),(int)food.getY())+food.getId(),an);
//                            mapSnake.foodList.add(food);
//                        }
//                    }
//                }
                //下面我们在遍历食物让食物移动
                List<Food> foodList=mapSnake.foodList;
                //System.out.println(foodList);
                foodList.forEach(e->{
                    //首先得到食物的移动的弧度
                    // int hash=getHash((int)e.getX(),(int)e.getY())+e.getId();
                    int curAngle=e.getAngle();
                    double cur=Math.PI*curAngle/180;
                    double addx=foodMove*Math.cos(cur);
                    double addy=foodMove*Math.sin(cur);
                    e.setX(e.getX()+addx);
                    if(e.getX()>=98){
                        //foodAngle.foodAngleHashMap.put(,Math.PI-curAngle);
                        e.setAngle(180-curAngle);
                    }else {
                        if(e.getX()<=2){
                            //  foodAngle.foodAngleHashMap.put(hash,Math.PI-curAngle);
                            e.setAngle(180-curAngle);
                        }
                    }
                    e.setY(e.getY()+addy);
                    if(e.getY()<=2){
                        //foodAngle.foodAngleHashMap.put(hash,2*Math.PI-curAngle);
                        e.setAngle(360-curAngle);
                    }else {
                        if(e.getY()>=98) {
                            //foodAngle.foodAngleHashMap.put(hash, 2 * Math.PI - curAngle);
                            e.setAngle(360-curAngle);
                        }
                    }
                });
                //下面让炸弹移动
                //下面出死亡算法
                Death(mapSnake.hashMap);
                moveBomb();
                ccc++;
                if(ccc==2) {
                    removeName.clear();
                    ccc=0;
                }
                //全部结束
                for (Map.Entry<String, Snake> entry : hashMap.entrySet()) {
                    entry.getValue().isV=true;//蛇被跟新
                }
            }
        }, 0, time);
    }
    //炸弹移动的算法
    public void moveBomb(){
        //得到炸弹数组
        Monster[] monsters=mapSnake.monsterLinkedList;
        HashMap<String,Snake> map=mapSnake.hashMap;
        if(map.size()==0){
            return;
        }
        for (int i = 0; i < monsters.length; i++) {
            double bombCenX = monsters[i].getX() + 1.5;
            double bombCenY = monsters[i].getY() + 1.5;
            double Mindis = Double.MAX_VALUE;
            Div minDiv = null;
            String minUser=null;
            for (Map.Entry<String, Snake> entry : map.entrySet()) {
                Div Head = entry.getValue().getSnakeBody().get(0);
                double cenX = Head.getTarLeft() + 1;
                double cenY = Head.getTarTop() + 1;
                //得到中心坐标
                double curDis = (bombCenX - cenX) * (bombCenX - cenX) + (bombCenY - cenY) * (bombCenY - cenY);
                if(curDis<6){
                    removeName.add(entry.getKey());
                    continue;
                }
                if (curDis < Mindis) {
                    minDiv = Head;
                    Mindis = curDis;
                    minUser=entry.getKey();
                }
            }
            if (minDiv != null) {
                double cenX = minDiv.getTarLeft();
                double cenY = minDiv.getTarTop();
                //炸弹开始移动
                if (minDiv != null) {
                    //得到中心
                    double sin,cos;
                    double h=1.8;
                    if (cenX > bombCenX && cenY < bombCenY) {
                        //右上移动 x加 y-
                        //double O = Math.acos((cenX - bombCenX) / Math.sqrt(Mindis));
                        //   System.out.println(O);
                        //在得到
//
//                        if(O<0){
//                            continue;
//                        }
//                        if(Double.isNaN(O)){
//                            O=0;
//                        }
//                        monsters[i].setX(monsters[i].getX() + Math.cos(O) * bombSpeed);
//                        monsters[i].setY(monsters[i].getY() - Math.sin(O) * bombSpeed);
                        //得到cos

                        cos=(cenX - bombCenX) / Math.sqrt(Mindis);
                        if(cos>1){
                            cos=1;
                            sin=0;
                        }else {
                            sin = Math.sqrt(1 - cos * cos);
                        }
                        if(minUser.equals("煌霸霸")&&Mindis<49&&mapSnake.hashMap.get(minUser).getI()==2){
                            cos*=-h;
                            sin*=-h;
                        }
                        monsters[i].setX(monsters[i].getX() + cos * bombSpeed);
                        monsters[i].setY(monsters[i].getY() - sin * bombSpeed);
                    } else {
                        if (cenX < bombCenX && cenY < bombCenY) {
                            //左上移动
                            // x- y-
//                            double O = Math.acos((bombCenX - cenX) / Math.sqrt(Mindis));
//                          //  System.out.println(O);
//                            if(O<0){
//                                continue;
//                            }
//                            if(Double.isNaN(O)){
//                                O=0;
//                            }
                            cos=( bombCenX-cenX) / Math.sqrt(Mindis);
                            if(cos>1){
                                cos=1;
                                sin=0;
                            }else {
                                sin = Math.sqrt(1 - cos * cos);
                            }
                            if(minUser.equals("煌霸霸")&&Mindis<49&&mapSnake.hashMap.get(minUser).getI()==2){
                                cos*=-h;
                                sin*=-h;
                            }
                            monsters[i].setX(monsters[i].getX() - cos * bombSpeed);
                            monsters[i].setY(monsters[i].getY() - sin * bombSpeed);
                        } else {
                            if (cenX > bombCenX && cenY > bombCenY) {
                                //右下移动
                                //x + y+
//                                double O = Math.acos((cenX - bombCenX) / Math.sqrt(Mindis));
//                                if(O<0){
//                                    continue;
//                                }
//                                if(Double.isNaN(O)){
//                                    O=0;
//                                }
                                cos=(cenX - bombCenX) / Math.sqrt(Mindis);
                                if(cos>1){
                                    cos=1;
                                    sin=0;
                                }else {
                                    sin = Math.sqrt(1 - cos * cos);
                                }
                                if(minUser.equals("煌霸霸")&&Mindis<49&&mapSnake.hashMap.get(minUser).getI()==2){
                                    cos*=-h;
                                    sin*=-h;
                                }
                                monsters[i].setX(monsters[i].getX() + cos * bombSpeed);
                                monsters[i].setY(monsters[i].getY() + sin * bombSpeed);
                            } else {
                                //左下移动
                                //y + x -
//                                double O = Math.acos(( bombCenX-cenX) / Math.sqrt(Mindis));
//                                //System.out.println(O);
//                                if(O<0){
//                                    continue;
//                                }
//                                if(Double.isNaN(O)){
//                                    O=0;
//                                }
                                cos=( bombCenX-cenX) / Math.sqrt(Mindis);
                                if(cos>1){
                                    cos=1;
                                    sin=0;
                                }else {
                                    sin = Math.sqrt(1 - cos * cos);
                                }
                                if(minUser.equals("煌霸霸")&&Mindis<49&&mapSnake.hashMap.get(minUser).getI()==2){
                                    cos*=-h;
                                    sin*=-h;
                                }
                                monsters[i].setX(monsters[i].getX() - cos * bombSpeed);
                                monsters[i].setY(monsters[i].getY() + sin * bombSpeed);
                            }
                        }
                    }
//                    if(Double.isNaN(sin)||Double.isNaN(cos)) {
//                        System.out.println("sin = " + sin + "  cos = " + cos);
//                    }
//                    for (Monster monster : monsters) {
//                        System.out.println("x = "+monster.getX()+" y = "+monster.getY());
//                    }
                    if(monsters[i].getX()>=98){
                        monsters[i].setX(0.6);
                    }
                    if(monsters[i].getX()<=0.5){
                        monsters[i].setX(100);
                    }
                    if(monsters[i].getY()>=98){
                        monsters[i].setY(0.6);
                    }
                    if(monsters[i].getY()<=0.5){
                        monsters[i].setY(100);
                    }
                }
            }
        }
    }
    //下面给出死亡算法
    public  void Death(HashMap<String,Snake> snakeHashMap){
        //我们先遍历蛇
        removeName.forEach(e->{
            if(snakeHashMap.containsKey(e)){
                snakeHashMap.remove(e);
            }
        });
        HashSet<String> deathName=new HashSet<>();
        for (Map.Entry<String, Snake> snakeEntry : snakeHashMap.entrySet()) {
            //得到头
            Div headDiv=snakeEntry.getValue().getSnakeBody().get(0);
            String curName=snakeEntry.getKey();
            double cenX=headDiv.getTarLeft()+1;
            double cenY=headDiv.getTarTop()+1;
            for (Map.Entry<String, Snake> entry : snakeHashMap.entrySet()) {
                //得到头
                if(!curName.equals(entry.getKey())&&!deathName.contains(entry.getKey())){
                    //得到这个的头
                    LinkedList<Div> linkedList=entry.getValue().getSnakeBody();
                    Div headOther=entry.getValue().getSnakeBody().get(0);
                    double cenOx=headOther.getTarLeft()+1;
                    double cenOy=headOther.getTarTop()+1;
                    double dis=(cenX-cenOx)*(cenX-cenOx)+(cenY-cenOy)*(cenY-cenOy);
                    if(dis<3.5){
                        deathName.add(curName);
                        removeName.add(curName);
                        //得到当前的挂的蛇
                        Snake snake=mapSnake.hashMap.get(curName);
                        //蛇死亡爆食物
                        Div deathHead=snake.getSnakeBody().get(0);
                        Food food=new Food(deathHead.getTarLeft(),deathHead.getTarTop(),1);
                        double rand=Math.random()*360;
                        //换算成弧度制度
                        food.setAngle((int) rand);
                        mapSnake.foodList.add(food);
                        break;
                    }
                    //头节点搞不死这蛇，遍历身体节点
                    int i=0;
                    for (Div div : linkedList) {
                        if(i!=0){
                            cenOx=div.getTarLeft()+1;
                            cenOy=div.getTarTop()+1;
                            dis=(cenX-cenOx)*(cenX-cenOx)+(cenY-cenOy)*(cenY-cenOy);
                            if(dis<3.2){
                                //当前蛇挂了
                                deathName.add(curName);
                                removeName.add(curName);
                                break;
                            }
                        }else {
                            i++;
                        }
                    }
                }
            }
        }
        removeName.forEach(e->{
            if(snakeHashMap.containsKey(e)){
                //移除sb蛇
                snakeHashMap.remove(e);
            }
        });
    }
    @ResponseBody
    @RequestMapping("first.do")
    public synchronized String firstOperation(Move move) {
        System.out.println("first.do");
        //System.out.println(move.getWidthBody()+"  "+move.getHeightBody());
        //System.out.println(move);
        //第一次接受到了
        if (mapSnake.hashMap.containsKey(move.getUsername())) {
            mapSnake.hashMap.get(move.getUsername()).setDirect(move.getDirect());
        } else {
            //创建snake对象
            //Snake snake = new Snake(move.getWidthBody(), move.getHeightBody(), move.getUsername(), move.getDirect(),move.getWidth(),move.getHeight());
            Snake snake=new Snake(move.getUsername(),move.getDirect(),move.getRadio());
            //把头做出来
            Div head = new Div(move.getTarLeft(), move.getTarTop());
            //更新身体
            snake.getSnakeBody().add(head);
            //把第二个玩意做出来
            Div body = head.copy();//new Div(move.getWidth(), move.getHeight(), move.getOtherLeft(), move.getOtherTop());
            snake.getSnakeBody().add(body);
            for (int i=0;i<10;i++){
                snake.getSnakeBody().add(body.copy());
            }
            //我们把怪物做出来
            if(mapSnake.monsterLinkedList.length>0&&mapSnake.monsterLinkedList[0]==null) {
//                Monster monster1 = getMonster();
//                Monster monster2 = getMonster();
//                mapSnake.monsterLinkedList[0]=(monster1);
//                mapSnake.monsterLinkedList[1]=(monster2);
                for (int i = 0; i < mapSnake.monsterLinkedList.length; i++) {
                    mapSnake.monsterLinkedList[i]=getMonster();
                }
            }
            //上怪物
            //遍历hashMap
            for (Map.Entry<String, Snake> entry : mapSnake.hashMap.entrySet()) {
                //加入nameSet里面
                snake.nameSet.add(entry.getKey());
            }
            mapSnake.hashMap.put(move.getUsername(), snake);
        }
        //http://localhost:8080/HuangbabaSnake/
        //huangbabasnake.vaiwan.com/HuangbabaSnake/
        //System.out.println(move);
        //System.out.println(mapSnake.hashMap);
        //如果是第一次登入，返回整张图的数据并打印。
        return gson.toJson(mapSnake);
    }
    @ResponseBody
    @RequestMapping("after.do")
    public String afterOperation(String username, int tar,int speed,boolean H) {
        SendMessage sendMessage=new SendMessage();//默认不修正
        SmallMap smallMap = new SmallMap();
        if (mapSnake.hashMap.containsKey(username)) {
            mapSnake.hashMap.get(username).setI(H?2:1);
            try {
                mapSnake.hashMap.get(username).direct = tar;
                mapSnake.hashMap.get(username).g();//设置判断掉线时间
                if(speed==1){
                    mapSnake.hashMap.get(username).setSpeed(true);
                }else {
                    mapSnake.hashMap.get(username).setSpeed(false);
                }
            } catch (Exception E) {
                System.out.println("出现了bug");
                System.out.println(E.getMessage());
            }
        }else {
            //sendMessage.smallMap=smallMap;
            return gson.toJson(sendMessage);
        }
        if (!mapSnake.hashMap.get(username).isV) {
            //这憨批地图还没有更新
            mapSnake.hashMap.get(username).delayCount=0;
            sendMessage.smallMap=smallMap;
            return gson.toJson(sendMessage);
        }
        if(mapSnake.hashMap.get(username).delayCount>1){
            sendMessage.isF=true;//刷新地图修正数
            sendMessage.mapSnake=mapSnake;
            mapSnake.hashMap.get(username).isV = false;
        }else {
            sendMessage.smallMap=smallMap;
            //如果是之后登入，只需放回头节点的信息即可
            //没有跟新，直接回去。
            smallMap.isH = true;//设置为ture；
            mapSnake.hashMap.get(username).isV = false;
            smallMap.foodList = mapSnake.foodList;//食物传回
            //传回死亡名单
            // smallMap.removeName = mapSnake.removeName;
            smallMap.map_new = new HashMap<>();
            smallMap.divHashMap = new HashMap<>();
            smallMap.monsterLinkedList=mapSnake.monsterLinkedList;//传递怪物信息
            //遍历mapSnake集合
            for (Map.Entry<String, Snake> snakeEntry : mapSnake.hashMap.entrySet()) {
                //当前蛇
                Snake curSnake = snakeEntry.getValue();
                if (curSnake.nameSet.size() == 0) {
                    //好蛇
                    //计算相对
                    Div div = curSnake.getSnakeBody().get(0);
                    double left = div.getTarLeft();// / curSnake.getWidthBody();
                    double top = div.getTarTop();// / curSnake.getHeightBody();
                    smallMap.divHashMap.put(curSnake.getUsername(), new HeadDiv(left, top, curSnake.getSnakeBody().size(),curSnake.getI()!=1));
                } else {
                    if (curSnake.nameSet.contains(username)) {
                        //坏蛇
                        curSnake.nameSet.remove(username);
                        smallMap.map_new.put(curSnake.getUsername(), curSnake);
                    } else {
                        Div div = curSnake.getSnakeBody().get(0);
                        double left = div.getTarLeft();// / curSnake.getWidthBody();
                        double top = div.getTarTop();// / curSnake.getHeightBody();
                        smallMap.divHashMap.put(curSnake.getUsername(), new HeadDiv(left, top, curSnake.getSnakeBody().size(),curSnake.getI()!=1));
                    }
                }
            }
        }
        //我们设置当前的蛇为延迟设置为0
        mapSnake.hashMap.get(username).delayCount=0;
        return gson.toJson(sendMessage);
    }
    //更新方向
    public int eatFood(double cXhead,double cYhead,List< Food> foodList,boolean is) {
        //double cXhead,double cYhead百分制坐标
        cXhead+=1;
        cYhead+=1;
        int addFoodCount=0;
        ArrayList<Integer> removeList=new ArrayList<>();
        int index=0;
        //  System.out.println(cXhead+" "+cYhead);
        for (Food food : foodList) {
            double cXfood=food.getX()+0.75;
            double cYfood=food.getY()+0.75;
            double dis=(cXfood-cXhead)*(cXfood-cXhead)+(cYfood-cYhead)*(cYfood-cYhead);
            //   System.out.println(" cxfood= "+cXfood+" cyfood= "+cYfood+" dis = "+dis);
            if(is){
                if (dis < 30.25) {
                    removeList.add(index);
                    if (food.getId() == 1) {
                        addFoodCount += 1;
                    } else {
                        if (food.getId() == 2) {
                            addFoodCount += 2;
                        } else {
                            addFoodCount += 3;
                        }
                    }
                }
            }else {
                if (dis < 3) {
                    removeList.add(index);
                    if (food.getId() == 1) {
                        addFoodCount += 1;
                    } else {
                        if (food.getId() == 2) {
                            addFoodCount += 2;
                        } else {
                            addFoodCount += 3;
                        }
                    }
                }
            }
            index++;
        }
        index=0;
        //我们再移除被吃掉的食物
        for (Integer integer : removeList) {
            foodList.remove(integer-index);
            index++;
        }
        return addFoodCount;
    }
    public boolean isadd() {
        double count = Math.random();
        if (count < 0.01) {
            return true;
        }
        return false;
    }
    public Food addFood() {
        //生成随机数，
        double xIndex = (Math.random() * 100);
        double yIndex = (Math.random() * 100);
        int id=(int)(Math.random()*3)+1;
        return new Food((int) xIndex, (int) yIndex,id);
    }
    //哈希函数，保证不会冲突
    public int getHash(int x, int y) {
        return 101 * x + 102 * y;
    }
    @Autowired
    RecordService recordService;
    //定义主动退出函数
    @ResponseBody
    @RequestMapping("active_exit.do")
    public String active_exit(String username,int score,Map<String,Object> map) {
        //把它移除
        //mapSnake.hashMap.remove(username);
        removeName.add(username);
        //mapSnake.hashMap.remove(username);
        //mapSnake.removeName.add(username);
        Record record=new Record();
        record.setScore(score);
        record.setType("联机");
        record.setUsername(username);
        recordService.insertRecord(record);
        map.put("username",username);
        return "start";
    }
    @RequestMapping("end.do")
    public String End(String username,int score,Map<String,Object> map){
        System.out.println("end.do");
        System.out.println(username+"  "+score);
        Record record=new Record();
        record.setScore(score);
        record.setType("联机");
        record.setUsername(username);
        recordService.insertRecord(record);
        System.out.println(username+"被杀死");
        map.put("username",username);
        return "start";
    }
    @RequestMapping("/Enter_the_team.do")
    public String Enter_the_team(Map<String,Object> map, String username){
        System.out.println("Enter_the_team.do");
        map.put("username",username);
        if(username==null){
            return "login";
        }
        if(mapSnake.hashMap.size()>=maxCount){
            return "start";
        }
        //map.put("username",username);
        return "Snake";
    }
    @ResponseBody
    @RequestMapping("/ifEnter.do")
    public String ifEnter(Map<String,Object> map, String username){
        System.out.println("ifEnter.do");
        if(mapSnake.hashMap.size()>=maxCount){
            return gson.toJson("no");
        }else {
            return gson.toJson("yes");
        }
    }
    public Monster getMonster(){
        double x=100*Math.random();
        double y=100*Math.random();
        return new Monster(x,y);
    }
    @ResponseBody
    @RequestMapping("/screen.change")
    public String screen_change( String username,double radio){
        System.out.println("/screen.change");
        mapSnake.hashMap.get(username).setRadio(radio);
        return "";
    }
}