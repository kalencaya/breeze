package com.liyu.breeze.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liyu.breeze.dao.entity.DiJobStepAttr;
import com.liyu.breeze.dao.mapper.DiJobStepAttrMapper;
import com.liyu.breeze.service.DiJobStepAttrService;
import com.liyu.breeze.service.convert.DiJobStepAttrConvert;
import com.liyu.breeze.service.dto.DiJobStepAttrDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


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

    @Override
    public int deleteSurplusStepAttr(Long jobId, List<String> linkStepList) {
        return this.diJobStepAttrMapper.delete(
                new LambdaQueryWrapper<DiJobStepAttr>()
                        .eq(DiJobStepAttr::getJobId, jobId)
                        .notIn(CollectionUtil.isNotEmpty(linkStepList), DiJobStepAttr::getStepCode, linkStepList)
        );
    }

    @Override
    public int upsert(DiJobStepAttrDTO diJobStepAttr) {
        DiJobStepAttr stepAttr = this.diJobStepAttrMapper.selectOne(
                new LambdaQueryWrapper<DiJobStepAttr>()
                        .eq(DiJobStepAttr::getJobId, diJobStepAttr.getJobId())
                        .eq(DiJobStepAttr::getStepCode, diJobStepAttr.getStepCode())
                        .eq(DiJobStepAttr::getStepAttrKey, diJobStepAttr.getStepAttrKey())
        );
        DiJobStepAttr attr = DiJobStepAttrConvert.INSTANCE.toDo(diJobStepAttr);
        if (stepAttr == null) {
            return this.diJobStepAttrMapper.insert(attr);
        } else {
            attr.setId(stepAttr.getId());
            return this.diJobStepAttrMapper.updateById(attr);
        }
    }

    @Override
    public List<DiJobStepAttrDTO> listJobStepAttr(Long jobId, String stepCode) {
        List<DiJobStepAttr> list = this.diJobStepAttrMapper.selectList(
                new LambdaQueryWrapper<DiJobStepAttr>()
                        .eq(DiJobStepAttr::getJobId, jobId)
                        .eq(DiJobStepAttr::getStepCode, stepCode)
        );
        return DiJobStepAttrConvert.INSTANCE.toDto(list);
    }
}
