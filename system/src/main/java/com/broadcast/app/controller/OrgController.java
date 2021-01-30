package com.broadcast.app.controller;

import com.broadcast.app.entity.Org;
import com.broadcast.app.entity.OrgTree;
import com.broadcast.app.service.OrgService;
import com.broadcast.app.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Org)表控制层
 *
 * @author makejava
 * @since 2021-01-26 18:47:45
 */
@RestController
@RequestMapping("org")
@CrossOrigin
public class OrgController {
    /**
     * 服务对象
     */
    @Resource
    private OrgService orgService;

    /**
     * 通过主键查询单条数据
     *

     * @return 单条数据
     */
    @GetMapping("selectOne")
    @ResponseBody
    public List<OrgTree> selectOne(String orgId) {
        return this.orgService.queryById(orgId);
    }

    @GetMapping("getOrgTree")
    @ResponseBody
    public Object getOrgTree(String orgId){
        List<OrgTree> orgTrees = orgService.queryById(orgId);
        return ResultUtil.success(orgTrees);

    }

}