package com.liyu.breeze.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.dao.entity.DiProject;
import com.liyu.breeze.dao.mapper.DiProjectMapper;
import com.liyu.breeze.service.DiDirectoryService;
import com.liyu.breeze.service.DiProjectService;
import com.liyu.breeze.service.convert.DiProjectConvert;
import com.liyu.breeze.service.dto.DiDirectoryDTO;
import com.liyu.breeze.service.dto.DiProjectDTO;
import com.liyu.breeze.service.param.DiProjectParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据集成-项目信息 服务实现类
 * </p>
 *
 * @author liyu
 */
@Service
public class DiProjectServiceImpl implements DiProjectService {

    @Autowired
    private DiProjectMapper diProjectMapper;

    @Autowired
    private DiDirectoryService diDirectoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(DiProjectDTO dto) {
        DiProject project = DiProjectConvert.INSTANCE.toDo(dto);
        int result = this.diProjectMapper.insert(project);
        DiDirectoryDTO dir = new DiDirectoryDTO();
        dir.setProjectId(project.getId());
        dir.setDirectoryName("/");
        dir.setPid(0L);
        this.diDirectoryService.insert(dir);
        return result;
    }

    @Override
    public int update(DiProjectDTO dto) {
        DiProject project = DiProjectConvert.INSTANCE.toDo(dto);
        return this.diProjectMapper.updateById(project);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long id) {
        this.diDirectoryService.deleteByProjectIds(new ArrayList<Long>() {{
            add(id);
        }});
        return this.diProjectMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(Map<Integer, ? extends Serializable> map) {
        this.diDirectoryService.deleteByProjectIds(map.values());
        return this.diProjectMapper.deleteBatchIds(map.values());
    }

    @Override
    public Page<DiProjectDTO> listByPage(DiProjectParam param) {
        Page<DiProjectDTO> result = new Page<>();
        Page<DiProject> list = this.diProjectMapper.selectPage(
                new Page<>(param.getCurrent(), param.getPageSize()),
                new LambdaQueryWrapper<DiProject>()
                        .like(StrUtil.isNotEmpty(param.getProjectCode()), DiProject::getProjectCode, param.getProjectCode())
                        .like(StrUtil.isNotEmpty(param.getProjectName()), DiProject::getProjectName, param.getProjectName())
        );
        List<DiProjectDTO> dtoList = DiProjectConvert.INSTANCE.toDto(list.getRecords());
        result.setCurrent(list.getCurrent());
        result.setSize(list.getSize());
        result.setRecords(dtoList);
        result.setTotal(list.getTotal());
        return result;
    }
}
