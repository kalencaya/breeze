package com.liyu.breeze.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liyu.breeze.dao.entity.Dept;
import com.liyu.breeze.dao.mapper.DeptMapper;
import com.liyu.breeze.service.DeptService;
import com.liyu.breeze.service.convert.DeptConvert;
import com.liyu.breeze.service.dto.DeptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author liyu
 * @since 2021-08-01
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public int insert(DeptDTO deptDTO) {
        Dept dept = DeptConvert.INSTANCE.toDo(deptDTO);
        return this.deptMapper.insert(dept);
    }

    @Override
    public int update(DeptDTO deptDTO) {
        Dept dept = DeptConvert.INSTANCE.toDo(deptDTO);
        return this.deptMapper.updateById(dept);
    }

    @Override
    public int deleteById(Long id) {
        return this.deptMapper.deleteById(id);
    }

    @Override
    public int deleteBatch(List<? extends Serializable> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        return this.deptMapper.deleteBatchIds(list);
    }

    @Override
    public DeptDTO selectOne(Long id) {
        Dept dept = this.deptMapper.selectById(id);
        return DeptConvert.INSTANCE.toDto(dept);
    }

    @Override
    public DeptDTO selectOne(String deptCode) {
        Dept dept = this.deptMapper.selectOne(new LambdaQueryWrapper<Dept>()
                .eq(Dept::getDeptCode, deptCode));
        return DeptConvert.INSTANCE.toDto(dept);
    }

    @Override
    public List<DeptDTO> listAll() {
        List<Dept> deptList = this.deptMapper.selectList(null);
        return DeptConvert.INSTANCE.toDto(deptList);
    }

    @Override
    public List<DeptDTO> listByDeptId(Long pid) {
        List<Dept> deptList = this.deptMapper.selectList(new LambdaQueryWrapper<Dept>()
                .eq(Dept::getPid, pid));
        return DeptConvert.INSTANCE.toDto(deptList);
    }
}
