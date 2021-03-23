package com.broadcast.app.service;

import com.broadcast.app.entity.Permission;
import com.broadcast.app.entity.PermissionTree;
import com.broadcast.app.entity.Rolepermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Permission)表服务接口
 *
 * @author makejava
 * @since 2021-03-14 17:10:13
 */
public interface PermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PermissionTree queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Permission> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param permission 实例对象
     * @return 实例对象
     */
    Permission insert(Permission permission);

    /**
     * 修改数据
     *
     * @param permission 实例对象
     * @return 实例对象
     */
    Permission update(Permission permission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    int bindRoleAndPermission(String rid,String pid);
    List<Rolepermission>  selectRolePerm( String rid, String pid);

}