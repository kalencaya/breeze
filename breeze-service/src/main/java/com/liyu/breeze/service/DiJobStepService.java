package com.liyu.breeze.service;

import com.liyu.breeze.service.dto.DiJobStepDTO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 数据集成-作业步骤信息 服务类
 * </p>
 *
 * @author liyu
 * @since 2022-03-10
 */
public interface DiJobStepService {
    /**
     * 按项目id删除
     *
     * @param projectIds project id
     * @return int
     */
    int deleteByProjectId(Collection<? extends Serializable> projectIds);

    /**
     * 按job id 删除
     *
     * @param jobIds job id
     * @return int
     */
    int deleteByJobId(Collection<? extends Serializable> jobIds);

    /**
     * 查询作业步骤信息
     *
     * @param jobId job id
     * @return job step list
     */
    List<DiJobStepDTO> listJobStep(Long jobId);
}
