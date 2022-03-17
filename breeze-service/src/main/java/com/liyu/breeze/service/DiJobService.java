package com.liyu.breeze.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.service.dto.DiJobDTO;
import com.liyu.breeze.service.param.DiJobParam;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 数据集成-作业信息 服务类
 * </p>
 *
 * @author liyu
 * @since 2022-03-10
 */
public interface DiJobService {
    /**
     * 新增
     *
     * @param dto info
     * @return int
     */
    int insert(DiJobDTO dto);

    /**
     * 修改
     *
     * @param dto info
     * @return int
     */
    int update(DiJobDTO dto);

    /**
     * 删除一个
     *
     * @param jobCode     jobCode
     * @param directoryId dir id
     * @return int
     */
    int deleteByCode(String jobCode, Long directoryId);

    /**
     * 批量删除
     *
     * @param list ids
     * @return int
     */
    int deleteByCode(List<DiJobDTO> list);

    /**
     * 物理删除项目下所有作业
     *
     * @param projectIds project id
     * @return int
     */
    int deleteByProjectId(Collection<Long> projectIds);

    /**
     * 分页
     *
     * @param param param
     * @return page list
     */
    Page<DiJobDTO> listByPage(DiJobParam param);

    /**
     * 返回id对应作业列表
     *
     * @param ids ids
     * @return list
     */
    List<DiJobDTO> listById(Collection<? extends Serializable> ids);

    /**
     * 查询job
     *
     * @param id id
     * @return info
     */
    DiJobDTO selectOne(Long id);


    /**
     * 项目下是否有有效的作业
     *
     * @param projectIds project iD
     * @return boolean
     */
    boolean hasValidJob(Collection<Long> projectIds);

    /**
     * 目录下是否存在有效作业
     *
     * @param projectId project id
     * @param dirId     dir id
     * @return boolean
     */
    boolean hasValidJob(Long projectId, Long dirId);

}
