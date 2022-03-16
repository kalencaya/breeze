package com.liyu.breeze.api.controller.di;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.vo.ResponseVO;
import com.liyu.breeze.service.DiJobService;
import com.liyu.breeze.service.dto.DiJobDTO;
import com.liyu.breeze.service.param.DiJobParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Api(tags = "数据集成-作业管理")
@RestController
@RequestMapping(path = "/api/di/job")
public class DiJobController {

    @Autowired
    private DiJobService diJobService;

    @Logging
    @GetMapping
    @ApiOperation(value = "分页查询作业列表", notes = "分页查询作业列表")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_SELECT)")
    public ResponseEntity<Page<DiJobDTO>> listJob(DiJobParam param) {
        Page<DiJobDTO> page = this.diJobService.listByPage(param);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Logging
    @PostMapping
    @ApiOperation(value = "新增作业", notes = "新增作业")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_ADD)")
    public ResponseEntity<ResponseVO> addJob(@Validated @RequestBody DiJobDTO diJobDTO) {
        //todo 新增作业时同步保存任务及任务相关属性step等
        this.diJobService.insert(diJobDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }

    @Logging
    @PutMapping
    @ApiOperation(value = "修改作业", notes = "修改作业")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<ResponseVO> editJob(@Validated @RequestBody DiJobDTO diJobDTO) {
        //todo 修改作业时，判断作业状态是否为发布，是则生产新的版本号插入作业，否则更新对应作业数据
        this.diJobService.update(diJobDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "删除作业", notes = "删除作业")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_DELETE)")
    public ResponseEntity<ResponseVO> deleteJob(@PathVariable(value = "jobCode") String jobCode) {
        //todo 删除作业时，判断作业是否为停止状态，是则物理删除作业所有版本，否则提示不能删除
        this.diJobService.deleteByCode(jobCode);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/batch")
    @ApiOperation(value = "批量删除作业", notes = "批量删除作业")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_DELETE)")
    public ResponseEntity<ResponseVO> deleteJob(@RequestBody Map<Integer, String> map) {
        //todo 删除作业时，判断作业是否为停止状态，是则物理删除作业所有版本，否则提示不能删除
        this.diJobService.deleteByCode(map);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

}
