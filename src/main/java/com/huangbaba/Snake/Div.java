package com.huangbaba.Snake;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther:huangbaba
 * @Date: 2021/12/9 - 12 - 09 - 9:32
 * @DEscription: com.huangbaba.User
 * @version: 1.0
 */
//@AllArgsConstructor
@NoArgsConstructor
@Data
public class Div{
    //蛇的每一个块
    //一下的全部是百分制度[0 100];
    double tarLeft;
    double tarTop;
    //double width;
    //double height;
    //下面是相对于屏幕的相对百分之几
//    double starLeft;
//    double starTop;
    public Div(double tarLeft,double tarTop){
        //this.width=width;
        //this.height=height;
        this.tarLeft=tarLeft;
        this.tarTop=tarTop;

    }
    public Div copy() {
        Div div = new Div();
        div.tarTop = this.tarTop;
        div.tarLeft = this.tarLeft;
      //  div.width = this.width;
        //div.height = this.height;
        return div;
    }
}
