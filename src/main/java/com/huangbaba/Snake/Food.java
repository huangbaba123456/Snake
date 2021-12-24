package com.huangbaba.Snake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
/**
 * @Auther:huangbaba
 * @Date: 2021/12/9 - 12 - 09 - 9:31
 * @DEscription: com.huangbaba.User
 * @version: 1.0
 */
//@AllArgsConstructor
@NoArgsConstructor
@Data
//食物
public class Food{
    //百分制
    double x;
    double y;
    public Food(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        /*Math.PI*curAngle/180*/
        this.angle=(int) (Math.random()*2*Math.PI);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Double.compare(food.x, x) == 0 &&
                Double.compare(food.y, y) == 0 &&
                id == food.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y, id);
    }
    //代表是哪一种食物 1代表蛋黄 2代表 西瓜  3 -。橙子
    int id;
    int angle;
//    public static boolean isadd(){
//        double count=Math.random();
//        if(count<0.001){
//            return true;
//        }
//        return false;
//    }
//    public static Food addFood(){
//        //生成随机数，
//        double xIndex=(Math.random()*100);
//        double yIndex=(Math.random()*100);
//        return new Food((int)xIndex,(int)yIndex);
//    }
    //这里是判断是否吃到食物
//    public static int eatFood(double headX, double headY, double width, double height,HashMap<String,Food> foodHashMap){
//        int x=(int) (headX/width*100);
//        int y=(int) (headY/height*100);
//        int count=0;
//        String temp=null;
//        temp=x+"_"+y;
//        if(foodHashMap.containsKey(temp)){
//            count++;
//            foodHashMap.remove(temp);
//        }
//        temp=x+1+"_"+y;
//        if(foodHashMap.containsKey(temp)){
//            count++;
//            foodHashMap.remove(temp);
//        }
//        temp=(x+1)+"_"+(y+1);
//        if(foodHashMap.containsKey(temp)){
//            count++;
//            foodHashMap.remove(temp);
//        }
//        temp=(x)+"_"+(y+1);
//        if(foodHashMap.containsKey(temp)){
//            count++;
//            foodHashMap.remove(temp);
//        }
//        temp=x-1+"_"+y;
//        if(foodHashMap.containsKey(temp)){
//            count++;
//            foodHashMap.remove(temp);
//        }
//        temp=(x-1)+"_"+(y-1);
//        if(foodHashMap.containsKey(temp)){
//            count++;
//            foodHashMap.remove(temp);
//        }
//        temp=(x)+"_"+(y-1);
//        if(foodHashMap.containsKey(temp)){
//            count++;
//            foodHashMap.remove(temp);
//        }
//        temp=x+2+"_"+y;
//        if(foodHashMap.containsKey(temp)){
//            count++;
//            foodHashMap.remove(temp);
//        }
//        temp=(x+2)+"_"+(y+2);
//        if(foodHashMap.containsKey(temp)){
//            count++;
//            foodHashMap.remove(temp);
//        }
//        temp=(x)+"_"+(y+2);
//        if(foodHashMap.containsKey(temp)){
//            count++;
//            foodHashMap.remove(temp);
//        }
//        return count;
//    }
}