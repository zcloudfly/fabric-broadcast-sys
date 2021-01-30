package com.broadcast.app.service;

import com.broadcast.app.entity.Appform;

import java.util.List;

/**
 * (Appform)表服务接口
 *
 * @author makejava
 * @since 2021-01-29 15:14:13
 */
public interface AppformService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Appform queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Appform> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param appform 实例对象
     * @return 实例对象
     */
    int insert(Appform appform);

    /**
     * 修改数据
     *
     * @param appform 实例对象
     * @return 实例对象
     */
    Appform update(Appform appform);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}