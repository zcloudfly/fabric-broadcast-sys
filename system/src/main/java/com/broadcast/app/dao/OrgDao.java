package com.broadcast.app.dao;

import com.broadcast.app.entity.Org;
import com.broadcast.app.entity.OrgTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Org)表数据库访问层
 *
 * @author makejava
 * @since 2021-01-26 18:47:30
 */
@Mapper
public interface OrgDao {

    /**
     * 通过ID查询单条数据
     *

     * @return 实例对象
     */
    List<OrgTree> queryById(@Param("orgId")String orgId);

    /**
     * 根据父id查询
     *

     * @return 实例对象
     */
    List<OrgTree> queryByPId(@Param("orgId")String orgId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Org> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param org 实例对象
     * @return 对象列表
     */
    List<Org> queryAll(Org org);

    /**
     * 新增数据
     *
     * @param org 实例对象
     * @return 影响行数
     */
    int insert(Org org);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Org> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Org> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Org> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Org> entities);

    /**
     * 修改数据
     *
     * @param org 实例对象
     * @return 影响行数
     */
    int update(Org org);

    /**
     * 通过主键删除数据
     *
     * @return 影响行数
     */
    int deleteById();

}