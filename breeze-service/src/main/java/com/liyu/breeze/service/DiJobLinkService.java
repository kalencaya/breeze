package com.liyu.breeze.service;

import com.liyu.breeze.service.dto.DiJobLinkDTO;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 数据集成-作业连线 服务类
 * </p>
 *
 * @author liyu
 * @since 2022-03-10
 */
public interface DiJobLinkService {

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
     * 查询作业连线信息
     *
     * @param jobId job id
     * @return job link list
     */
    List<DiJobLinkDTO> listJobLink(Long jobId);
}
