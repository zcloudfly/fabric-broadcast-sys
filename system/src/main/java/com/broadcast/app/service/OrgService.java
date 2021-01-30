package com.broadcast.app.service;

import com.broadcast.app.entity.Org;
import com.broadcast.app.entity.OrgTree;

import java.util.List;

/**
 * (Org)表服务接口
 *
 * @author makejava
 * @since 2021-01-26 18:47:35
 */
public interface OrgService {

    /**
     * 通过ID查询单条数据
     *
     * @param orgId 主键
     * @return 实例对象
     */
    List<OrgTree> queryById(String orgId);
    /**
     * 通过ID查询单条数据
     *
     * @param orgId 主键
     * @return 实例对象
     */
    List<OrgTree> queryByPId(String orgId) ;

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Org> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param org 实例对象
     * @return 实例对象
     */
    Org insert(Org org);

    /**
     * 修改数据
     *
     * @param org 实例对象
     * @return 实例对象
     */
    Org update(Org org);



}