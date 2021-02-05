package com.broadcast.app.controller;

import com.broadcast.app.entity.Attach;
import com.broadcast.app.service.AttachService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Attach)表控制层
 *
 * @author makejava
 * @since 2021-02-02 21:33:14
 */
@RestController
@RequestMapping("attach")
public class AttachController {
    /**
     * 服务对象
     */
    @Resource
    private AttachService attachService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Attach selectOne(String id) {
        return this.attachService.queryById(id);
    }

}