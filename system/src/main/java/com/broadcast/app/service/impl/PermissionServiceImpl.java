package com.broadcast.app.service.impl;

import com.broadcast.app.dao.PermissionDao;
import com.broadcast.app.entity.Permission;
import com.broadcast.app.entity.PermissionTree;
import com.broadcast.app.entity.Rolepermission;
import com.broadcast.app.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Permission)表服务实现类
 *
 * @author makejava
 * @since 2021-03-14 17:10:14
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionDao permissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PermissionTree queryById(String id) {
        return this.permissionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Permission> queryAllByLimit(int offset, int limit) {
        return this.permissionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param permission 实例对象
     * @return 实例对象
     */
    @Override
    public Permission insert(Permission permission) {
        this.permissionDao.insert(permission);
        return permission;
    }

    /**
     * 修改数据
     *
     * @param permission 实例对象
     * @return 实例对象
     */
    @Override
    public Permission update(Permission permission) {
        this.permissionDao.update(permission);
        return null;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.permissionDao.deleteById(id) > 0;
    }

    @Override
    public int bindRoleAndPermission(String rid,String pid){
        return this.permissionDao.bindRoleAndPermission(rid,pid);
    }

    @Override
    public List<Rolepermission>  selectRolePerm(String rid, String pid) {
        return this.permissionDao.selectRolePerm(rid,pid);
    }
}