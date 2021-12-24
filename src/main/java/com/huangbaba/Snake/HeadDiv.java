package com.huangbaba.Snake;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther:huangbaba
 * @Date: 2021/12/12 - 12 - 12 - 17:03
 * @DEscription: com.huangbaba.User
 * @version: 1.0
 */
@NoArgsConstructor
@Data
//该类是负责后期只给
public class HeadDiv {
    //这是相对坐标，【1-100】;
    public double starLeft;
    public double starTop;
    //当前这条共有几个,因为蛇是不会变短的
    public int count;
    public boolean H;
    public HeadDiv(double starLeft,double starTop,int count,boolean H){
        this.starLeft=starLeft;
        this.starTop=starTop;
        this.count=count;
        this.H=H;
    }
}
