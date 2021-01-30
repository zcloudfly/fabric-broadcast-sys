package com.broadcast.app.controller;

import com.broadcast.app.entity.User;
import com.broadcast.app.service.FabricService;
import com.broadcast.app.service.UserService;
import com.broadcast.app.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2021-01-24 12:04:23
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private FabricService fs;
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public User selectOne(String id) {
        return this.userService.queryById(id);
    }
    @GetMapping("test")
    public String  t(){
        return fs.test();
    }

    @PostMapping(value = "/login")
    @CrossOrigin
    @ResponseBody
    public Object login(@RequestBody User user){

        User resuser = this.userService.queryByPwdAndName(user);
         if (StringUtils.isEmpty(resuser)){
             return ResultUtil.error("用户名或密码错误！");
         }else{
             return ResultUtil.success(resuser);
         }
    }

}