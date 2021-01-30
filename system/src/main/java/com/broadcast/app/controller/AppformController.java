package com.broadcast.app.controller;

import com.broadcast.app.entity.Appform;
import com.broadcast.app.service.AppformService;
import com.broadcast.app.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * (Appform)表控制层
 *
 * @author makejava
 * @since 2021-01-29 15:14:23
 */
@RestController
@RequestMapping("appform")
@CrossOrigin
public class AppformController {
    /**
     * 服务对象
     */
    @Resource
    private AppformService appformService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Appform selectOne(String id) {
        return this.appformService.queryById(id);
    }


    @PostMapping("insert")
    public Object insertAppForm(@RequestBody   Appform sendForm){
        String id= UUID.randomUUID().toString().replace("-","");
        sendForm.setId(id);
        int i = appformService.insert(sendForm);
        if(i>0){
            return ResultUtil.success("提交成功");
        }else{
            return ResultUtil.error("提交失败");
        }

    }

}