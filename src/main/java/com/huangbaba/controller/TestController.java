package com.huangbaba.controller;
import com.huangbaba.Snake.Tar;
import com.huangbaba.pojo.Record;
import com.huangbaba.pojo.Users;
import com.huangbaba.service.RecordService;
import com.huangbaba.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @Auther:huangbaba
 * @Date: 2021/10/14 - 10 - 14 - 20:00
 * @DEscription: com.huangbaba.controller
 * @version: 1.0
 */
@Controller
public class TestController {
    @Autowired
    UserService userService;
    @Autowired
    RecordService recordService;
    @RequestMapping("/test.do")
    public String test(Map<String,Object> map, String username, String password,String H){
        if(H!=null){
            map.put("username",username);
            return "Snake";
        }
        Users user=userService.findUser(username);
        System.out.println("\n*********************\n");
        System.out.println("username = "+username);
        System.out.println("password = "+password);
        System.out.println(user);
        System.out.println("\n*********************\n");
        if(user==null){
            System.out.println("用户不存在");
            map.put("username",username);
            map.put("password",password);
            return "login";
        }else {
            if(user.getPassword().equals(password)){
                System.out.println("登入成功");
                map.put("username",username);
                map.put("password",password);
                return "start";
            }else {
                System.out.println("密码错误");
                map.put("username",username);
                map.put("password",password);
                return "login";
            }
        }
    }
    @RequestMapping("/")
    public String login(){
        System.out.println("login");
        return "login";
    }
    @ResponseBody
    @RequestMapping("testajax.do")
    public String testajax(Tar tar){
        testajax1(tar);
        return testajax2();
    }
    @ResponseBody
    @RequestMapping("testajax1.do")
    public String testajax1(Tar tar){
        tar.setValue(tar.getValue()+1);
        hashMap.put(tar.getName(),tar);
        System.out.println(" username "+tar.getName()+" value = "+tar.getValue());
        return "";
    }
    @ResponseBody
    @RequestMapping("testajax2.do")
    public String testajax2(){
        System.out.println("这时的map");
        ArrayList<Tar> arrayList=new ArrayList<>();
        for (Map.Entry<String,Tar> entry:hashMap.entrySet() ){
            System.out.println(entry.getKey()+" "+entry.getValue().getValue());
            arrayList.add(entry.getValue());
        }
        Gson gson=new Gson();
        String s=gson.toJson(arrayList);
        return s;
    }
//    @ResponseBody
    @RequestMapping("huang.do")
    public String huang(){
        System.out.println("huang.do");
        return  "login";
    }
    HashMap<String,Tar> hashMap=new HashMap<>();
    @ResponseBody
    @RequestMapping("startp.do")
    public String selectrand(){
        //System.out.println("startp.dp");
        List<Record> list=new ArrayList<>();
        Gson gson=new Gson();
        List<Users> usersList=userService.findAllUser();
        for (Users users : usersList) {
            List<Record> recordList=recordService.selectRecord(users.getUsername());
            if(recordList.size()>0) {
                list.add(recordList.get(0));
            }
        }
        //System.out.println(list);
        //排序
        Collections.sort(list,(e1,e2)->{
            return e2.getScore()-e1.getScore();
        });
        //可以在这里选择前几，这里不选择了
        /*
        * */
        String s=gson.toJson(list);
        return s;
    }
    @RequestMapping("/djs.do")
    public String djs(){
        return "play";
    }
}
