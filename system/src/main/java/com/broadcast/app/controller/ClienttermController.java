package com.broadcast.app.controller;

import com.alibaba.fastjson.JSONArray;
import com.broadcast.app.controller.vo.ClienttermVo;
import com.broadcast.app.controller.vo.PlstVo;
import com.broadcast.app.entity.Clientterm;
import com.broadcast.app.service.ClienttermService;
import com.broadcast.app.service.FabricService;
import com.broadcast.app.util.FabricUtil;
import com.broadcast.app.util.Result;
import com.broadcast.app.util.ResultUtil;
import com.github.pagehelper.Page;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Clientterm)表控制层
 *
 * @author makejava
 * @since 2021-02-02 21:33:46
 */
@RestController
@RequestMapping("/clientterm")

public class ClienttermController {
    /**
     * 服务对象
     */
    @Resource
    private ClienttermService clienttermService;
    @Resource
    private FabricService fabricService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public Result selectOne(String id) {
        Clientterm client=this.clienttermService.queryById(id);
        return ResultUtil.success(client);

    }

    @PostMapping("/selectbyquery")
    @CrossOrigin
    @ResponseBody
    public Result selectbyquery(@RequestBody ClienttermVo vo) {
        int total = this.clienttermService.selecttotal(vo);
        vo.setTotal(total);
        int pageNum=vo.getCurrentPage();
        int pageSize=vo.getPagesize();
        int offset=vo.getCurrentPage() > 0 ? (pageNum - 1) * pageSize : 0;
        vo.setOffset(offset);
        vo.setLimit( offset + pageSize * (pageNum > 0 ? 1 : 0));
        List<Clientterm> clients=this.clienttermService.queryAllByLimit(vo);

        return ResultUtil.successPage(total,clients);


    }
    @GetMapping("/getCliPlst")
    @CrossOrigin
    @ResponseBody
    public Result getCliPlst(String clientid){
        String result=null;
       try {
            result = fabricService.query("mychannel", "play1_2_1", clientid);
            if(result!=null){
                result=result.replace("\\","");
                result=result.substring(1,result.length()-1);
            }
         //  List<PlstVo> plstVos = JSONArray.parseArray(result, PlstVo.class);
           return ResultUtil.success(result);
       }catch (Exception e){
           e.printStackTrace();
           return ResultUtil.error("null");
       }


    }

}