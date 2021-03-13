package com.broadcast.app.controller;

import com.broadcast.app.service.FabricService;
import com.broadcast.app.util.FabricUtil;
import com.broadcast.app.util.Result;

import com.broadcast.app.util.ResultUtil;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Calendar;

@RestController
@RequestMapping("/fabric")
public class FabricController {

    @Resource
    private  FabricService service;

    /**
     * 查询某条数据
     * @param channel
     * @param cont
     * @param args
     * @return
     */
    @RequestMapping("/query")
    public Result query(String channel, String cont, String[] args){
        String query=null;
        try {
            Gateway gateway = FabricUtil.geteWay();
            Contract contact = FabricUtil.contact(gateway, channel, cont);
            query = service.query(channel,cont, args);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(e.getMessage());
        }
        return ResultUtil.success(query);
    }
    @RequestMapping("/invoke")
    public Result invoke(String channel,String cont, String[] args){
        try {
            Gateway gateway = FabricUtil.geteWay();
            Contract contact = FabricUtil.contact(gateway, channel, cont);
            return ResultUtil.success(service.invoke(gateway, channel, cont,args));
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(e.getMessage());
        }
    }
}
