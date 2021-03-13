package com.broadcast.app.dao;

import com.broadcast.app.controller.vo.ClienttermVo;
import com.broadcast.app.entity.Clientterm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Clientterm)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-02 21:33:42
 */
@Mapper
public interface ClienttermDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Clientterm queryById(String id);

    /**
     * 查询指定行数据
     *
     * @return 对象列表
     */
    List<Clientterm> queryAllByLimit(ClienttermVo vo);

    List<Clientterm> queryAll(Clientterm clientterm);
    /**
     * 通过实体作为筛选条件查询
     *
     * @param vo 实例对象
     * @return 对象列表
     */
    int querytotal(ClienttermVo vo);

    /**
     * 新增数据
     *
     * @param clientterm 实例对象
     * @return 影响行数
     */
    int insert(Clientterm clientterm);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Clientterm> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Clientterm> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Clientterm> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Clientterm> entities);

    /**
     * 修改数据
     *
     * @param clientterm 实例对象
     * @return 影响行数
     */
    int update(Clientterm clientterm);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}