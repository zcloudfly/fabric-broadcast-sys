package com.broadcast.app.service.impl;

import com.broadcast.app.dao.OrgDao;
import com.broadcast.app.entity.Org;
import com.broadcast.app.entity.OrgTree;
import com.broadcast.app.service.OrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Org)表服务实现类
 *
 * @author makejava
 * @since 2021-01-26 18:47:38
 */
@Service("orgService")
public class OrgServiceImpl implements OrgService {
    @Resource
    private OrgDao orgDao;

    /**
     * 通过ID查询单条数据
     *

     * @return 实例对象
     */
    @Override
    public List<OrgTree> queryById(String orgId) {
        return this.orgDao.queryById(orgId);
    }
    /**
     * 通过ID查询单条数据
     *

     * @return 实例对象
     */
    @Override
    public List<OrgTree> queryByPId(String orgId) {
        return this.orgDao.queryByPId( orgId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Org> queryAllByLimit(int offset, int limit) {
        return this.orgDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param org 实例对象
     * @return 实例对象
     */
    @Override
    public Org insert(Org org) {
        this.orgDao.insert(org);
        return org;
    }

    @Override
    public Org update(Org org) {
        return null;
    }

    /**
     * 修改数据
     *
     * @param org 实例对象
     * @return 实例对象
     */



}