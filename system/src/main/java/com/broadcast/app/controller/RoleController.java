package com.broadcast.app.controller;

import com.broadcast.app.controller.vo.RoleVo;
import com.broadcast.app.controller.vo.UserVo;
import com.broadcast.app.entity.Role;
import com.broadcast.app.entity.User;
import com.broadcast.app.service.RoleService;
import com.broadcast.app.util.ResultUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * (Role)表控制层
 *
 * @author makejava
 * @since 2021-03-14 11:24:24
 */
@RestController
@RequestMapping("role")
@CrossOrigin
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Role selectOne(String id) {
        return this.roleService.queryById(id);
    }

    @PostMapping(value = "/getRoleByWhere")
    public Object getUserByWhere(@RequestBody RoleVo vo){
        int total= this.roleService.queryAllTotal(vo);
        List<Role> role=this.roleService.queryAll(vo);
        return ResultUtil.successPage(total,role);
    }

    @PostMapping(value = "/addRole")
    public Object addRole(@RequestBody Role role){
        try {
            role.setId(System.currentTimeMillis()+"");
            role.setCreatetime(new Date());
            Role insert = this.roleService.insert(role);
            return ResultUtil.success(insert);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error(e.getMessage());
        }
    }

    @GetMapping(value = "/deletRole")
    public Object deletRole(String id){
        boolean b = this.roleService.deleteById(id);
        if(b){
            return ResultUtil.success("删除成功");
        }else{
            return ResultUtil.error("删除失败");
        }
    }
    @GetMapping(value = "/getUserRole")
    public Object getUserRole( String uid){
        List<Role> userRole = this.roleService.getUserRole(uid);
        List<String> collect = userRole.stream().map(Role::getId).collect(Collectors.toList());
        return ResultUtil.success(collect);
    }

    @GetMapping(value = "/insertUserRole")
    public Object insertUserRole( String uid, String[] rids){
        try {
            for (String rid : rids) {
                int i=this.roleService.insertUserRole(uid, rid);
                System.out.println(i);
            }
            return ResultUtil.success();
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }
    }

}