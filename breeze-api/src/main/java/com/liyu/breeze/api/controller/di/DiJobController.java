package com.liyu.breeze.api.controller.di;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.util.I18nUtil;
import com.liyu.breeze.api.util.SecurityUtil;
import com.liyu.breeze.api.vo.DiJobAttrVO;
import com.liyu.breeze.api.vo.ResponseVO;
import com.liyu.breeze.common.constant.DictConstants;
import com.liyu.breeze.common.enums.*;
import com.liyu.breeze.service.DiJobAttrService;
import com.liyu.breeze.service.DiJobLinkService;
import com.liyu.breeze.service.DiJobService;
import com.liyu.breeze.service.DiJobStepService;
import com.liyu.breeze.service.dto.DiJobAttrDTO;
import com.liyu.breeze.service.dto.DiJobDTO;
import com.liyu.breeze.service.dto.DiJobLinkDTO;
import com.liyu.breeze.service.dto.DiJobStepDTO;
import com.liyu.breeze.service.param.DiJobParam;
import com.liyu.breeze.service.vo.DictVO;
import com.liyu.breeze.service.vo.JobGraphVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "数据集成-作业管理")
@RestController
@RequestMapping(path = "/api/di/job")
public class DiJobController {

    @Autowired
    private DiJobService diJobService;
    @Autowired
    private DiJobAttrService diJobAttrService;
    @Autowired
    private DiJobStepService diJobStepService;
    @Autowired
    private DiJobLinkService diJobLinkService;


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
    @ApiOperation(value = "新增作业记录", notes = "新增一条作业记录，相关流程定义不涉及")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_ADD)")
    public ResponseEntity<ResponseVO> simpleAddJob(@Validated @RequestBody DiJobDTO diJobDTO) {
        String currentUser = SecurityUtil.getCurrentUserName();
        diJobDTO.setJobOwner(currentUser);
        diJobDTO.setJobStatus(new DictVO(JobStatusEnum.DRAFT.getValue(), JobStatusEnum.DRAFT.getLabel()));
        diJobDTO.setRuntimeState(new DictVO(JobRuntimeStateEnum.STOP.getValue(), JobRuntimeStateEnum.STOP.getLabel()));
        diJobDTO.setJobVersion(1);
        this.diJobService.insert(diJobDTO);
        return new ResponseEntity<>(ResponseVO.sucess(diJobDTO), HttpStatus.CREATED);
    }

    @Logging
    @PutMapping
    @ApiOperation(value = "修改作业记录", notes = "只修改作业记录属性，相关流程定义不改变")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<ResponseVO> simpleEditJob(@Validated @RequestBody DiJobDTO diJobDTO) {
        this.diJobService.update(diJobDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @DeleteMapping(path = "/{id}")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "删除作业", notes = "删除作业")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_DELETE)")
    public ResponseEntity<ResponseVO> deleteJob(@PathVariable(value = "id") Long id) {
        DiJobDTO job = this.diJobService.selectOne(id);
        if (job == null) {
            return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
        } else if (JobRuntimeStateEnum.STOP.getValue().equals(job.getRuntimeState().getValue())) {
            this.diJobService.deleteByCode(job.getJobCode(), job.getDirectory().getId());
            return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                    I18nUtil.get("response.error.di.runningJob"), ErrorShowTypeEnum.NOTIFICATION), HttpStatus.OK);
        }
    }

    @Logging
    @PostMapping(path = "/batch")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "批量删除作业", notes = "批量删除作业")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_DELETE)")
    public ResponseEntity<ResponseVO> deleteJob(@RequestBody Map<Integer, String> map) {
        List<DiJobDTO> list = this.diJobService.listById(map.values());
        if (CollectionUtil.isEmpty(list)) {
            return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
        }
        //任意作业不是停止状态则不能删除
        boolean flag = true;
        for (DiJobDTO dto : list) {
            if (!JobRuntimeStateEnum.STOP.getValue().equals(dto.getRuntimeState().getValue())) {
                flag = false;
            }
        }
        if (flag) {
            this.diJobService.deleteByCode(list);
            return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                    I18nUtil.get("response.error.di.runningJob"), ErrorShowTypeEnum.NOTIFICATION), HttpStatus.OK);
        }
    }


    @Logging
    @GetMapping(path = "/detail")
    @ApiOperation(value = "查询作业详情", notes = "查询作业详情，包含作业流程定义信息")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_SELECT)")
    public ResponseEntity<DiJobDTO> getJobDetail(Long id) {
        DiJobDTO job = this.diJobService.selectOne(id);
        List<DiJobAttrDTO> jobAttrList = this.diJobAttrService.listJobAttr(id);
        List<DiJobLinkDTO> jobLinkList = this.diJobLinkService.listJobLink(id);
        List<DiJobStepDTO> jobStepList = this.diJobStepService.listJobStep(id);
        job.setJobAttrList(jobAttrList);
        job.setJobLinkList(jobLinkList);
        job.setJobStepList(jobStepList);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @Logging
    @PutMapping(path = "/detail")
    @ApiOperation(value = "修改作业详情", notes = "修改作业相关流程定义")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<ResponseVO> editJobDetail(@Validated @RequestBody DiJobDTO diJobDTO) {
        //todo 修改作业详情时，判断作业状态是否为发布，是则生产新的版本号插入作业，否则更新对应作业数据
        this.diJobService.update(diJobDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/detail")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "保存作业详情", notes = "保存作业相关流程定义")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<ResponseVO> saveJobDetail(@Validated @RequestBody DiJobDTO diJobDTO) {
        String cellKey = "cells";
        String stepShape = "angular-shape";
        String linkShape = "edge";
        if (CollectionUtil.isNotEmpty(diJobDTO.getJobGraph())) {
            Map<String, List<JobGraphVO>> map = diJobDTO.getJobGraph();
            if (CollectionUtil.isNotEmpty(map) && map.containsKey(cellKey)) {
                List<JobGraphVO> list = map.get(cellKey);
                // 清除途中已删除的连线信息
                List<String> linkList = list.stream()
                        .filter(j -> linkShape.equals(j.getShape()))
                        .map(JobGraphVO::getId)
                        .collect(Collectors.toList());
                this.diJobLinkService.deleteSurplusLink(diJobDTO.getId(), linkList);
                //清除图中已删除的节点信息及节点属性
                List<String> stepList = list.stream()
                        .filter(j -> stepShape.equals(j.getShape()))
                        .map(JobGraphVO::getId)
                        .collect(Collectors.toList());
                this.diJobStepService.deleteSurplusStep(diJobDTO.getId(), stepList);
                if (CollectionUtil.isNotEmpty(list)) {
                    for (JobGraphVO graph : list) {
                        if (stepShape.equals(graph.getShape())) {
                            //插入新的，更新已有的 这里不处理节点属性信息
                            DiJobStepDTO jobStep = new DiJobStepDTO();
                            jobStep.setJobId(diJobDTO.getId());
                            jobStep.setStepCode(graph.getId());
                            jobStep.setStepTitle(getStepAttrByKey(graph, "title", ""));
                            String type = getStepAttrByKey(graph, "type", "");
                            jobStep.setStepType(DictVO.toVO(DictConstants.JOB_STEP_TYPE, type));
                            jobStep.setStepName(getStepAttrByKey(graph, "name", ""));
                            jobStep.setPositionX(getPositionByKey(graph, "x", 0));
                            jobStep.setPositionY(getPositionByKey(graph, "y", 0));
                            this.diJobStepService.upsert(jobStep);
                        }
                        if (linkShape.equals(graph.getShape())) {
                            //插入新的
                            DiJobLinkDTO jobLink = new DiJobLinkDTO();
                            jobLink.setLinkCode(graph.getId());
                            jobLink.setJobId(diJobDTO.getId());
                            jobLink.setFromStepCode(graph.getSource().getCell());
                            jobLink.setToStepCode(graph.getTarget().getCell());
                            this.diJobLinkService.upsert(jobLink);
                        }
                    }
                }
            }
        }
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }

    private String getStepAttrByKey(JobGraphVO graph, String key, String defaultValue) {
        if (graph == null) {
            return defaultValue;
        }
        Map<String, Object> dataList = graph.getData();
        if (CollectionUtil.isNotEmpty(dataList) && dataList.containsKey(key)) {
            return String.valueOf(dataList.get(key));
        } else {
            return defaultValue;
        }
    }

    private Integer getPositionByKey(JobGraphVO graph, String key, Integer defaultValue) {
        if (graph == null) {
            return defaultValue;
        }
        Map<String, Integer> position = graph.getPosition();
        if (CollectionUtil.isNotEmpty(position) && position.containsKey(key)) {
            return position.get(key);
        } else {
            return defaultValue;
        }
    }

    @Logging
    @GetMapping(path = "/attr/{jobId}")
    @ApiOperation(value = "查询作业属性", notes = "查询作业属性列表")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<DiJobAttrVO> listJobAttr(@PathVariable(value = "jobId") Long jobId) {
        DiJobAttrVO vo = new DiJobAttrVO();
        vo.setJobId(jobId);
        List<DiJobAttrDTO> list = this.diJobAttrService.listJobAttr(jobId);
        for (DiJobAttrDTO jobAttr : list) {
            String str = jobAttr.getJobAttrKey().concat("=").concat(jobAttr.getJobAttrValue());
            if (JobAttrTypeEnum.JOB_ATTR.getValue().equals(jobAttr.getJobAttrType().getValue())) {
                String tempStr = StrUtil.isEmpty(vo.getJobAttr()) ? "" : vo.getJobAttr();
                vo.setJobAttr(tempStr + str + "\n");
            } else if (JobAttrTypeEnum.JOB_PROP.getValue().equals(jobAttr.getJobAttrType().getValue())) {
                String tempStr = StrUtil.isEmpty(vo.getJobProp()) ? "" : vo.getJobProp();
                vo.setJobProp(tempStr + str + "\n");
            } else if (JobAttrTypeEnum.ENGINE_PROP.getValue().equals(jobAttr.getJobAttrType().getValue())) {
                String tempStr = StrUtil.isEmpty(vo.getEngineProp()) ? "" : vo.getEngineProp();
                vo.setEngineProp(tempStr + str + "\n");
            }
        }
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/attr")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "修改作业属性", notes = "修改作业属性信息")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<ResponseVO> saveJobAttr(@RequestBody DiJobAttrVO jobAttrVO) {
        Map<String, DiJobAttrDTO> map = new HashMap<>();
        DictVO jobAttrtype = DictVO.toVO(DictConstants.JOB_ATTR_TYPE, JobAttrTypeEnum.JOB_ATTR.getValue());
        DictVO jobProptype = DictVO.toVO(DictConstants.JOB_ATTR_TYPE, JobAttrTypeEnum.JOB_PROP.getValue());
        DictVO engineProptype = DictVO.toVO(DictConstants.JOB_ATTR_TYPE, JobAttrTypeEnum.ENGINE_PROP.getValue());
        parseJobAttr(map, jobAttrVO.getJobAttr(), jobAttrtype, jobAttrVO.getJobId());
        parseJobAttr(map, jobAttrVO.getJobProp(), jobProptype, jobAttrVO.getJobId());
        parseJobAttr(map, jobAttrVO.getEngineProp(), engineProptype, jobAttrVO.getJobId());
        this.diJobAttrService.deleteByJobId(new ArrayList<Long>() {{
            add(jobAttrVO.getJobId());
        }});
        for (Map.Entry<String, DiJobAttrDTO> entry : map.entrySet()) {
            this.diJobAttrService.upsert(entry.getValue());
        }
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    private void parseJobAttr(Map<String, DiJobAttrDTO> map, String str, DictVO jobAttrType, Long jobId) {
        if (StrUtil.isNotEmpty(str)) {
            String[] lines = str.split("\n");
            for (String line : lines) {
                String[] kv = line.split("=");
                if (kv.length == 2 && StrUtil.isAllNotBlank(kv)) {
                    DiJobAttrDTO dto = new DiJobAttrDTO();
                    dto.setJobId(jobId);
                    dto.setJobAttrType(jobAttrType);
                    dto.setJobAttrKey(kv[0]);
                    dto.setJobAttrValue(kv[1]);
                    map.put(jobId + jobAttrType.getValue() + kv[0], dto);
                }
            }
        }
    }

    ;
}
