package com.broadcast.app.service;

import com.broadcast.app.controller.vo.ClienttermVo;
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

     * @return 对象列表
     */
    List<Clientterm> queryAllByLimit(ClienttermVo vo);

    /**
     *
     * @param vo
     * @return
     */
    public int selecttotal(ClienttermVo vo);
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


    List<Clientterm> queryAll(Clientterm clientterm);

}