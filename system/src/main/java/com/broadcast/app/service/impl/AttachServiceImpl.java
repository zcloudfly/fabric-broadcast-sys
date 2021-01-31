package com.broadcast.app.service.impl;

import com.broadcast.app.dao.AttachDao;
import com.broadcast.app.entity.Attach;
import com.broadcast.app.service.AttachService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Attach)表服务实现类
 *
 * @author makejava
 * @since 2021-01-31 12:46:41
 */
@Service("attachService")
public class AttachServiceImpl implements AttachService {
    @Resource
    private AttachDao attachDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Attach queryById(String id) {
        return this.attachDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Attach> queryAllByLimit(int offset, int limit) {
        return this.attachDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param attach 实例对象
     * @return 实例对象
     */
    @Override
    public Attach insert(Attach attach) {
        this.attachDao.insert(attach);
        return attach;
    }

    /**
     * 修改数据
     *
     * @param attach 实例对象
     * @return 实例对象
     */
    @Override
    public Attach update(Attach attach) {
        this.attachDao.update(attach);
        return this.queryById(attach.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.attachDao.deleteById(id) > 0;
    }
}