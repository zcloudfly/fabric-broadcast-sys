package com.broadcast.app.service.impl;

import com.broadcast.app.controller.vo.RoleVo;
import com.broadcast.app.dao.RoleDao;
import com.broadcast.app.entity.Role;
import com.broadcast.app.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2021-03-14 11:24:22
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Role queryById(String id) {
        return this.roleDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Role> queryAllByLimit(int offset, int limit) {
        return this.roleDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @Override
    public Role insert(Role role) {
        this.roleDao.insert(role);
        return role;
    }

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    @Override
    public Role update(Role role) {
        this.roleDao.update(role);
        return this.queryById(role.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.roleDao.deleteById(id) > 0;
    }

    @Override
    public int queryAllTotal(RoleVo role) {
        return this.roleDao.queryAllTotal(role);
    }

    @Override
    public List<Role> getUserRole(String uid) {
        return this.roleDao.getUserRole(uid);
    }

    @Override
    public int insertUserRole(String uid, String rid) {
        return this.roleDao.insertUserRole(uid,rid);
    }

    @Override
    public List<Role> queryAll(RoleVo role){
        return this.roleDao.queryAll(role);
    }
}