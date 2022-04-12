package com.liyu.breeze.service.di.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.common.enums.JobStatusEnum;
import com.liyu.breeze.dao.entity.DiJob;
import com.liyu.breeze.dao.mapper.DiJobMapper;
import com.liyu.breeze.service.convert.DiJobConvert;
import com.liyu.breeze.service.di.*;
import com.liyu.breeze.service.dto.DiDirectoryDTO;
import com.liyu.breeze.service.dto.DiJobDTO;
import com.liyu.breeze.service.param.DiJobParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author gleiyu
 */
@Service
public class DiJobServiceImpl implements DiJobService {

    @Autowired
    private DiJobMapper diJobMapper;

    @Autowired
    private DiJobAttrService diJobAttrService;

    @Autowired
    private DiJobLinkService diJobLinkService;

    @Autowired
    private DiJobStepService diJobStepService;

    @Autowired
    private DiJobStepAttrService diJobStepAttrService;

    @Autowired
    private DiDirectoryService diDirectoryService;

    @Override
    public DiJobDTO insert(DiJobDTO dto) {
        DiJob job = DiJobConvert.INSTANCE.toDo(dto);
        this.diJobMapper.insert(job);
        dto.setId(job.getId());
        return dto;
    }

    @Override
    public int archive(String jobCode, Long directoryId) {
        int result = 0;
        List<DiJob> list = this.diJobMapper.selectList(
                new LambdaQueryWrapper<DiJob>()
                        .eq(DiJob::getJobCode, jobCode)
                        .eq(DiJob::getDirectoryId, directoryId)
                        .eq(DiJob::getJobStatus, JobStatusEnum.RELEASE.getValue())
                        .orderByAsc(DiJob::getJobVersion)
        );
        if (CollectionUtil.isEmpty(list)) {
            return 0;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            DiJob job = new DiJob();
            job.setId(list.get(i).getId());
            job.setJobStatus(JobStatusEnum.ARCHIVE.getValue());
            result += this.diJobMapper.update(job,
                    new LambdaUpdateWrapper<DiJob>()
                            .eq(DiJob::getId, job.getId())
            );
        }
        return result;
    }

    @Override
    public int update(DiJobDTO dto) {
        DiJob job = DiJobConvert.INSTANCE.toDo(dto);
        return this.diJobMapper.updateById(job);
    }

    @Override
    public int deleteByCode(String jobCode, Long directoryId) {
        List<DiJob> jobList = this.diJobMapper.selectList(new LambdaQueryWrapper<DiJob>()
                .eq(DiJob::getJobCode, jobCode)
                .eq(DiJob::getDirectoryId, directoryId)
        );
        List<Long> ids = jobList.stream().map(DiJob::getId).collect(Collectors.toList());
        this.diJobAttrService.deleteByJobId(ids);
        this.diJobLinkService.deleteByJobId(ids);
        this.diJobStepService.deleteByJobId(ids);
        this.diJobStepAttrService.deleteByJobId(ids);
        return this.diJobMapper.delete(new LambdaQueryWrapper<DiJob>()
                .eq(DiJob::getJobCode, jobCode)
                .eq(DiJob::getDirectoryId, directoryId)
        );
    }

    @Override
    public int deleteByCode(List<DiJobDTO> list) {
        int result = 0;
        for (DiJobDTO dto : list) {
            result += deleteByCode(dto.getJobCode(), dto.getDirectory().getId());
        }
        return result;
    }


    @Override
    public int deleteByProjectId(Collection<? extends Serializable> projectIds) {
        this.diJobAttrService.deleteByProjectId(projectIds);
        this.diJobLinkService.deleteByProjectId(projectIds);
        this.diJobStepService.deleteByProjectId(projectIds);
        this.diJobStepAttrService.deleteByProjectId(projectIds);
        return this.diJobMapper.delete(new LambdaQueryWrapper<DiJob>()
                .in(DiJob::getProjectId, projectIds));
    }


    @Override
    public Page<DiJobDTO> listByPage(DiJobParam param) {
        Page<DiJobDTO> result = new Page<>();
        Page<DiJob> list = this.diJobMapper.selectPage(
                new Page<>(param.getCurrent(), param.getPageSize()),
                param.toDo()
        );
        List<DiJobDTO> dtoList = DiJobConvert.INSTANCE.toDto(list.getRecords());
        Map<Long, DiDirectoryDTO> map = this.diDirectoryService.loadFullPath(list.getRecords().stream().map(DiJob::getDirectoryId).collect(Collectors.toList()));
        for (DiJobDTO job : dtoList) {
            DiDirectoryDTO dir = map.get(job.getDirectory().getId());
            job.setDirectory(dir);
        }
        result.setCurrent(list.getCurrent());
        result.setSize(list.getSize());
        result.setRecords(dtoList);
        result.setTotal(list.getTotal());
        return result;
    }

    @Override
    public List<DiJobDTO> listById(Collection<? extends Serializable> ids) {
        List<DiJob> list = this.diJobMapper.selectList(
                new LambdaQueryWrapper<DiJob>().in(DiJob::getId, ids)
        );
        List<DiJobDTO> dtoList = DiJobConvert.INSTANCE.toDto(list);
        Map<Long, DiDirectoryDTO> map = this.diDirectoryService.loadFullPath(list.stream().map(DiJob::getDirectoryId).collect(Collectors.toList()));
        for (DiJobDTO job : dtoList) {
            DiDirectoryDTO dir = map.get(job.getDirectory().getId());
            job.setDirectory(dir);
        }
        return dtoList;
    }

    @Override
    public DiJobDTO selectOne(Long id) {
        DiJob job = this.diJobMapper.selectById(id);
        DiJobDTO dto = DiJobConvert.INSTANCE.toDto(job);
        Map<Long, DiDirectoryDTO> map = this.diDirectoryService.loadFullPath(new ArrayList<Long>() {{
            add(job.getDirectoryId());
        }});
        DiDirectoryDTO dir = map.get(job.getDirectoryId());
        dto.setDirectory(dir);
        return dto;
    }


    @Override
    public boolean hasValidJob(Collection<Long> projectIds) {
        DiJob job = this.diJobMapper.selectOne(new LambdaQueryWrapper<DiJob>()
                .in(DiJob::getProjectId, projectIds)
                .last("limit 1")
        );
        if (job == null || job.getId() == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean hasValidJob(Long projectId, Long dirId) {
        DiJob job = this.diJobMapper.selectOne(
                new LambdaQueryWrapper<DiJob>()
                        .eq(DiJob::getProjectId, projectId)
                        .eq(DiJob::getDirectoryId, dirId)
                        .last("limit 1")

        );
        if (job == null || job.getId() == null) {
            return false;
        } else {
            return true;
        }
    }


}
