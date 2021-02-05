package com.broadcast.app.service;

import com.broadcast.app.entity.Clientterm;

import java.util.List;

/**
 * (Clientterm)表服务接口
 *
 * @author makejava
 * @since 2021-02-02 21:33:43
 */
public interface ClienttermService {

    /**
     * 通过ID查询单条数据
     *
     * @param id主键
     * @return 实例对象
     */
    Clientterm queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Clientterm> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param clientterm 实例对象
     * @return 实例对象
     */
    Clientterm insert(Clientterm clientterm);

    /**
     * 修改数据
     *
     * @param clientterm 实例对象
     * @return 实例对象
     */
    Clientterm update(Clientterm clientterm);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}