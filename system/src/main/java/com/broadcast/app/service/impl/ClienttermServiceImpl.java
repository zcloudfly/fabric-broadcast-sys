package com.broadcast.app.service.impl;

import com.broadcast.app.dao.ClienttermDao;
import com.broadcast.app.entity.Clientterm;
import com.broadcast.app.service.ClienttermService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Clientterm)表服务实现类
 *
 * @author makejava
 * @since 2021-02-02 21:33:44
 */
@Service("clienttermService")
public class ClienttermServiceImpl implements ClienttermService {
    @Resource
    private ClienttermDao clienttermDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Clientterm queryById(String id) {
        return this.clienttermDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Clientterm> queryAllByLimit(int offset, int limit) {
        return this.clienttermDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param clientterm 实例对象
     * @return 实例对象
     */
    @Override
    public Clientterm insert(Clientterm clientterm) {
        this.clienttermDao.insert(clientterm);
        return clientterm;
    }

    /**
     * 修改数据
     *
     * @param clientterm 实例对象
     * @return 实例对象
     */
    @Override
    public Clientterm update(Clientterm clientterm) {
        this.clienttermDao.update(clientterm);
        return this.queryById(clientterm.getClientid());
    }

    /**
     * 通过主键删除数据
     *
     * @param id主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.clienttermDao.deleteById(id) > 0;
    }
}