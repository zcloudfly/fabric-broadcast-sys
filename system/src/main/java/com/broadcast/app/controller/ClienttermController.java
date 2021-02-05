package com.broadcast.app.controller;

import com.broadcast.app.entity.Clientterm;
import com.broadcast.app.service.ClienttermService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (Clientterm)表控制层
 *
 * @author makejava
 * @since 2021-02-02 21:33:46
 */
@RestController
@RequestMapping("clientterm")
public class ClienttermController {
    /**
     * 服务对象
     */
    @Resource
    private ClienttermService clienttermService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Clientterm selectOne(String id) {
        return this.clienttermService.queryById(id);
    }

}