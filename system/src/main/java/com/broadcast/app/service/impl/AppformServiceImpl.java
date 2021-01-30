package com.broadcast.app.service.impl;

import com.broadcast.app.dao.AppformDao;
import com.broadcast.app.entity.Appform;
import com.broadcast.app.service.AppformService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Appform)表服务实现类
 *
 * @author makejava
 * @since 2021-01-29 15:14:16
 */
@Service("appformService")
public class AppformServiceImpl implements AppformService {
    @Resource
    private AppformDao appformDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Appform queryById(String id) {
        return this.appformDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Appform> queryAllByLimit(int offset, int limit) {
        return this.appformDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param appform 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(Appform appform) {
        return this.appformDao.insert(appform);

    }

    /**
     * 修改数据
     *
     * @param appform 实例对象
     * @return 实例对象
     */
    @Override
    public Appform update(Appform appform) {
        this.appformDao.update(appform);
        return this.queryById(appform.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.appformDao.deleteById(id) > 0;
    }
}