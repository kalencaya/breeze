package com.liyu.breeze.service.impl;

import com.liyu.breeze.dao.mapper.DiJobStepAttrMapper;
import com.liyu.breeze.service.DiJobStepAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;


@Service
public class DiJobStepAttrServiceImpl implements DiJobStepAttrService {

    @Autowired
    private DiJobStepAttrMapper diJobStepAttrMapper;

    @Override
    public int deleteByProjectId(Collection<? extends Serializable> projectIds) {
        return this.diJobStepAttrMapper.deleteByProjectId(projectIds);
    }

    @Override
    public int deleteByJobId(Collection<? extends Serializable> jobIds) {
        return this.diJobStepAttrMapper.deleteByJobId(jobIds);
    }
}
