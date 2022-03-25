package com.liyu.breeze.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liyu.breeze.dao.entity.DiJobLink;
import com.liyu.breeze.dao.mapper.DiJobLinkMapper;
import com.liyu.breeze.service.DiJobLinkService;
import com.liyu.breeze.service.convert.DiJobLinkConvert;
import com.liyu.breeze.service.dto.DiJobLinkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author gleiyu
 */
@Service
public class DiJobLinkServiceImpl implements DiJobLinkService {

    @Autowired
    private DiJobLinkMapper diJobLinkMapper;

    @Override
    public int insert(DiJobLinkDTO diJobLink) {
        DiJobLink link = DiJobLinkConvert.INSTANCE.toDo(diJobLink);
        return this.diJobLinkMapper.insert(link);
    }

    @Override
    public int deleteByProjectId(Collection<? extends Serializable> projectIds) {
        return this.diJobLinkMapper.deleteByProjectId(projectIds);
    }

    @Override
    public int deleteByJobId(Collection<? extends Serializable> jobIds) {
        return this.diJobLinkMapper.deleteByJobId(jobIds);
    }

    @Override
    public List<DiJobLinkDTO> listJobLink(Long jobId) {
        List<DiJobLink> list = this.diJobLinkMapper.selectList(
                new LambdaQueryWrapper<DiJobLink>()
                        .eq(DiJobLink::getJobId, jobId)
        );
        return DiJobLinkConvert.INSTANCE.toDto(list);
    }

    @Override
    public int deleteSurplusLink(Long jobId, List<String> linkCodeList) {
        return this.diJobLinkMapper.delete(
                new LambdaQueryWrapper<DiJobLink>()
                        .eq(DiJobLink::getJobId, jobId)
                        .notIn(DiJobLink::getLinkCode, linkCodeList)
        );
    }

    @Override
    public int upsert(DiJobLinkDTO diJobLink) {
        DiJobLink link = this.diJobLinkMapper.selectOne(
                new LambdaQueryWrapper<DiJobLink>()
                        .eq(DiJobLink::getJobId, diJobLink.getJobId())
                        .eq(DiJobLink::getLinkCode, diJobLink.getLinkCode())
        );
        DiJobLink jobLink = DiJobLinkConvert.INSTANCE.toDo(diJobLink);
        if (link == null) {
            return this.diJobLinkMapper.insert(jobLink);
        } else {
            jobLink.setId(link.getId());
            return this.diJobLinkMapper.updateById(jobLink);
        }
    }
}
