package com.liyu.breeze.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.dao.entity.DiJob;
import com.liyu.breeze.dao.mapper.DiJobMapper;
import com.liyu.breeze.service.DiDirectoryService;
import com.liyu.breeze.service.DiJobService;
import com.liyu.breeze.service.convert.DiJobConvert;
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
 * 1.新增作业时，默认为草稿状态
 * 2.作业只有发布后才能运行，点击运行时判断当前版本是否已发布，提示自动发布
 * 3。发布作业：每个作业只有一个发布版本，历史发布的版本全部改为归档
 * 4.修改作业保存时，判断当前版本是否为发布，是则新生成版本保存，否则覆盖保存
 * 5. 删除作业时，判断作业是否为停止状态，是则物理删除相关所有记录，否则不能删除，且提示需停止任务
 */
@Service
public class DiJobServiceImpl implements DiJobService {

    @Autowired
    private DiJobMapper diJobMapper;

    @Autowired
    private DiDirectoryService diDirectoryService;

    @Override
    public int insert(DiJobDTO dto) {
        DiJob job = DiJobConvert.INSTANCE.toDo(dto);
        return this.diJobMapper.insert(job);
    }

    @Override
    public int update(DiJobDTO dto) {
        DiJob job = DiJobConvert.INSTANCE.toDo(dto);
        return this.diJobMapper.updateById(job);
    }

    @Override
    public int deleteByCode(String jobCode) {
        //todo 将作业所有的历史版本全部更改为删除，如果作业当前有发布版本，则需要先下线任务
        return this.diJobMapper.delete(new LambdaQueryWrapper<DiJob>()
                .eq(DiJob::getJobCode, jobCode));
    }

    @Override
    public int deleteByCode(Map<Integer, ? extends Serializable> map) {
        return this.diJobMapper.delete(new LambdaQueryWrapper<DiJob>()
                .in(DiJob::getJobCode, map.values())
        );
    }


    @Override
    public int deleteByProjectId(Collection<Long> projectIds) {
        //todo 同步删除相关step数据
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
