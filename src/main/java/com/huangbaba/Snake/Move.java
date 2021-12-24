package com.huangbaba.Snake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther:huangbaba
 * @Date: 2021/11/20 - 11 - 20 - 12:39
 * @DEscription: com.huangbaba.pojo
 * @version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Move {
    //四个东西足以
    String username;
    int direct;
    double tarLeft;
    double tarTop;
    double radio;
//    double widthBody;
//    double heightBody;
//    double otherLeft;
//    double otherTop;
//    double width;
//    double height;
    /*
    * username: username,
      direct: tarDirect,
      tarLeft: tarLeft,
      tarTop: tarTop,
      * */
}
