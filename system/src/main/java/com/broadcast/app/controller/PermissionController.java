package com.broadcast.app.controller;

import com.broadcast.app.controller.vo.RoleVo;
import com.broadcast.app.entity.OrgTree;
import com.broadcast.app.entity.Permission;
import com.broadcast.app.entity.PermissionTree;
import com.broadcast.app.entity.Rolepermission;
import com.broadcast.app.service.PermissionService;
import com.broadcast.app.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * (Permission)表控制层
 *
 * @author makejava
 * @since 2021-03-14 17:10:15
 */
@RestController
@RequestMapping("permission")
@CrossOrigin
public class PermissionController {
    /**
     * 服务对象
     */
    @Resource
    private PermissionService permissionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public PermissionTree selectOne(String id) {
        return this.permissionService.queryById(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("getTree")
    @ResponseBody
    public Object getOrgTree(String id){
        PermissionTree permissionTree = permissionService.queryById(id);
        List<PermissionTree> trees=permissionTree.getChildren();
        return ResultUtil.success(trees);

    }
    @PostMapping ("insert")
    @ResponseBody
    public Object insert(@RequestBody Permission per){
        try {
            per.setCreatetime(new Date());
            Permission insert = this.permissionService.insert(per);
            return ResultUtil.success();
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }
    }
    @GetMapping("delete")
    public Object deleteNode(String id){
        PermissionTree permissionTree = permissionService.queryById(id);
        List<PermissionTree> trees=permissionTree.getChildren();
        if(trees.size()>0){
            return ResultUtil.error("请先删除子节点！");
        }
        boolean b = permissionService.deleteById(id);
        if(b) {
            return ResultUtil.success("删除成功");
        }else{
            return ResultUtil.error("删除失败");
        }
    }
    @PostMapping("/bindRoleAndPermission")
    public Object bindRoleAndPermission(@RequestBody  RoleVo vo){
        List<Rolepermission>  rolepermissions=null;
        for (PermissionTree tree:vo.getPtree() ) {
            rolepermissions = this.permissionService.selectRolePerm(vo.getId(), tree.getId());
            if(rolepermissions.size()==0) {
                this.permissionService.bindRoleAndPermission(vo.getId(), tree.getId());
            }
        }
        return ResultUtil.success();
    }

    @PostMapping("/getRoleAndPermission")
    public Object getRoleAndPermission(@RequestBody  RoleVo vo){
        List<Rolepermission>  rolepermissions=this.permissionService.selectRolePerm(vo.getId(),null);
        List<String> pids=null;
        if(rolepermissions.size()>0){
           pids = rolepermissions.stream().map(Rolepermission::getPid).collect(Collectors.toList());
        }
        return ResultUtil.success(pids);
    }
}