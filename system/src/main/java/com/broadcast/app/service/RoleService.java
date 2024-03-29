package com.broadcast.app.service;

import com.broadcast.app.controller.vo.RoleVo;
import com.broadcast.app.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Role)表服务接口
 *
 * @author makejava
 * @since 2021-03-14 11:24:21
 */
public interface RoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Role queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Role> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Role insert(Role role);

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Role update(Role role);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);
    List<Role> queryAll(RoleVo role);
    int queryAllTotal(RoleVo role);
    List<Role> getUserRole( String uid);
    int insertUserRole(String uid,String rid);

}