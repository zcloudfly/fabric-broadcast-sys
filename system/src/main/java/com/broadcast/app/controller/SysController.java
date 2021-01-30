package com.broadcast.app.controller;

import com.broadcast.app.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class SysController {


    @PostMapping  (value = "/login")
    @CrossOrigin
    @ResponseBody
    public Map aaaaa(@RequestBody User user){
        HashMap<String, String> resmap = new HashMap<>();
        if(user.getName().equals("zhangsan")&&user.getPwd().equals("123456")){
            resmap.put("code","0");
            resmap.put("msg","登陆成功");
            return resmap;
        }else{
            resmap.put("code","-1");
            resmap.put("msg","登陆失败");
            return resmap;
        }
    }
}
