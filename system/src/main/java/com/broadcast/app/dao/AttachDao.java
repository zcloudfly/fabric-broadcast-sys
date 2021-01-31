package com.broadcast.app.dao;

import com.broadcast.app.entity.Attach;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Attach)表数据库访问层
 *
 * @author makejava
 * @since 2021-01-31 12:46:39
 */
@Mapper
public interface AttachDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Attach queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Attach> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param attach 实例对象
     * @return 对象列表
     */
    List<Attach> queryAll(Attach attach);

    /**
     * 新增数据
     *
     * @param attach 实例对象
     * @return 影响行数
     */
    int insert(Attach attach);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Attach> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Attach> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Attach> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Attach> entities);

    /**
     * 修改数据
     *
     * @param attach 实例对象
     * @return 影响行数
     */
    int update(Attach attach);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}