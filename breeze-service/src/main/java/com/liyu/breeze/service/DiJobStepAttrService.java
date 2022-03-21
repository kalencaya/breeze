package com.liyu.breeze.service;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 数据集成-作业步骤参数 服务类
 * </p>
 *
 * @author liyu
 * @since 2022-03-10
 */
public interface DiJobStepAttrService {

    /**
     * 按项目id删除
     * @param projectIds project id
     * @return int
     */
    int deleteByProjectId(Collection<? extends Serializable> projectIds);

    /**
     * 按job id 删除
     * @param jobIds job id
     * @return int
     */
    int deleteByJobId(Collection<? extends  Serializable> jobIds);
}
