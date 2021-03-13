package com.broadcast.app.service;

import com.broadcast.app.entity.Attach;

import java.util.List;

/**
 * (Attach)表服务接口
 *
 * @author makejava
 * @since 2021-01-31 12:46:40
 */
public interface AttachService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Attach queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Attach> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param attach 实例对象
     * @return 实例对象
     */
    Attach insert(Attach attach);

    /**
     * 修改数据
     *
     * @param attach 实例对象
     * @return 实例对象
     */
    Attach update(Attach attach);

    /**
     * 通过主键删除数据
     *
     * @param  id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     *
     * @param attach
     * @return
     */
    List<Attach> queryAll(Attach attach);

}