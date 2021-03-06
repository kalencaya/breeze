package com.liyu.breeze.api.controller.di;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.sliew.flinkful.cli.base.CliClient;
import cn.sliew.flinkful.cli.base.PackageJarJob;
import cn.sliew.flinkful.cli.descriptor.DescriptorCliClient;
import cn.sliew.flinkful.common.enums.DeploymentTarget;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.util.I18nUtil;
import com.liyu.breeze.api.util.SecurityUtil;
import com.liyu.breeze.api.vo.DiJobAttrVO;
import com.liyu.breeze.api.vo.DiJobRunVO;
import com.liyu.breeze.api.vo.ResponseVO;
import com.liyu.breeze.common.constant.Constants;
import com.liyu.breeze.common.constant.DictConstants;
import com.liyu.breeze.common.enums.*;
import com.liyu.breeze.engine.util.JobConfigHelper;
import com.liyu.breeze.service.admin.SystemConfigService;
import com.liyu.breeze.service.di.*;
import com.liyu.breeze.service.dto.di.*;
import com.liyu.breeze.service.param.di.DiJobParam;
import com.liyu.breeze.service.storage.StorageService;
import com.liyu.breeze.service.storage.impl.NioFileServiceImpl;
import com.liyu.breeze.service.vo.DictVO;
import com.liyu.breeze.service.vo.JobGraphVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.JobID;
import org.apache.flink.client.deployment.executors.RemoteExecutor;
import org.apache.flink.configuration.*;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gleiyu
 */
@Slf4j
@Api(tags = "????????????-????????????")
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
    @Autowired
    private DiJobStepAttrService diJobStepAttrService;
    @Autowired
    private DiJobStepAttrTypeService diJobStepAttrTypeService;
    @Autowired
    private JobConfigHelper jobConfigHelper;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private DiJobResourceFileService diJobResourceFileService;
    @Autowired
    private DiClusterConfigService diClusterConfigService;
    @Resource(name = "${app.resource.type}")
    private StorageService storageService;

    @Logging
    @GetMapping
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_SELECT)")
    public ResponseEntity<Page<DiJobDTO>> listJob(DiJobParam param) {
        Page<DiJobDTO> page = this.diJobService.listByPage(param);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Logging
    @PostMapping
    @ApiOperation(value = "??????????????????", notes = "??????????????????????????????????????????????????????")
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
    @ApiOperation(value = "??????????????????", notes = "?????????????????????????????????????????????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<ResponseVO> simpleEditJob(@Validated @RequestBody DiJobDTO diJobDTO) {
        this.diJobService.update(diJobDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @DeleteMapping(path = "/{id}")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "????????????", notes = "????????????")
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
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_DELETE)")
    public ResponseEntity<ResponseVO> deleteJob(@RequestBody Map<Integer, String> map) {
        List<DiJobDTO> list = this.diJobService.listById(map.values());
        if (CollectionUtil.isEmpty(list)) {
            return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
        }
        //?????????????????????????????????????????????
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
    @ApiOperation(value = "??????????????????", notes = "???????????????????????????????????????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_SELECT)")
    public ResponseEntity<DiJobDTO> getJobDetail(Long id) {
        DiJobDTO job = queryJobInfo(id);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }


    @Logging
    @PostMapping(path = "/detail")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "??????????????????", notes = "??????????????????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<ResponseVO> saveJobDetail(@Validated @RequestBody DiJobDTO diJobDTO) {
        DiJobDTO job = this.diJobService.selectOne(diJobDTO.getId());
        if (JobStatusEnum.RELEASE.getValue().equals(job.getJobStatus().getValue())) {
            int jobVersion = job.getJobVersion() + 1;
            job.setId(null);
            job.setJobVersion(jobVersion);
            job.setJobStatus(DictVO.toVO(DictConstants.JOB_STATUS, JobStatusEnum.DRAFT.getValue()));
            DiJobDTO newJob = this.diJobService.insert(job);
            diJobDTO.setId(newJob.getId());

        } else if (JobStatusEnum.ARCHIVE.getValue().equals(job.getJobStatus().getValue())) {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                    I18nUtil.get("response.error.di.archivedJob"), ErrorShowTypeEnum.NOTIFICATION), HttpStatus.OK);
        }
        saveJobGraph(diJobDTO.getJobGraph(), diJobDTO.getId());
        return new ResponseEntity<>(ResponseVO.sucess(diJobDTO.getId()), HttpStatus.CREATED);
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

    private void saveJobGraph(Map<String, List<JobGraphVO>> jobGraph, Long jobId) {
        String cellKey = "cells";
        String stepShape = "angular-shape";
        String linkShape = "edge";
        if (CollectionUtil.isNotEmpty(jobGraph)) {
            Map<String, List<JobGraphVO>> map = jobGraph;
            if (map.containsKey(cellKey)) {
                List<JobGraphVO> list = map.get(cellKey);
                // ????????????????????????????????????
                List<String> linkList = list.stream()
                        .filter(j -> linkShape.equals(j.getShape()))
                        .map(JobGraphVO::getId)
                        .collect(Collectors.toList());
                this.diJobLinkService.deleteSurplusLink(jobId, linkList);
                //???????????????????????????????????????????????????
                List<String> stepList = list.stream()
                        .filter(j -> stepShape.equals(j.getShape()))
                        .map(JobGraphVO::getId)
                        .collect(Collectors.toList());
                this.diJobStepService.deleteSurplusStep(jobId, stepList);
                if (CollectionUtil.isNotEmpty(list)) {
                    for (JobGraphVO graph : list) {
                        if (stepShape.equals(graph.getShape())) {
                            //?????????????????????????????? ?????????????????????????????????
                            DiJobStepDTO jobStep = new DiJobStepDTO();
                            jobStep.setJobId(jobId);
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
                            //????????????
                            DiJobLinkDTO jobLink = new DiJobLinkDTO();
                            jobLink.setLinkCode(graph.getId());
                            jobLink.setJobId(jobId);
                            jobLink.setFromStepCode(graph.getSource().getCell());
                            jobLink.setToStepCode(graph.getTarget().getCell());
                            this.diJobLinkService.upsert(jobLink);
                        }
                    }
                }
            }
        }
    }

    @Logging
    @GetMapping(path = "/attr/{jobId}")
    @ApiOperation(value = "??????????????????", notes = "????????????????????????")
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
    @ApiOperation(value = "??????????????????", notes = "????????????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<ResponseVO> saveJobAttr(@RequestBody DiJobAttrVO jobAttrVO) {
        DiJobDTO jobInfo = this.diJobService.selectOne(jobAttrVO.getJobId());
        if (JobStatusEnum.ARCHIVE.getValue().equals(jobInfo.getJobStatus().getValue())) {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                    I18nUtil.get("response.error.di.archivedJob"), ErrorShowTypeEnum.NOTIFICATION), HttpStatus.OK);
        }
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


    @Logging
    @GetMapping(path = "/attrType")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<List<DiJobStepAttrTypeDTO>> listJobStepAttrType(@NotBlank String stepType, @NotBlank String stepName) {
        List<DiJobStepAttrTypeDTO> list = this.diJobStepAttrTypeService.listByType(stepType, stepName);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/step")
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<List<DiJobStepAttrDTO>> listDiJobStepAttr(@NotBlank String jobId, @NotBlank String stepCode) {
        List<DiJobStepAttrDTO> list = this.diJobStepAttrService.listJobStepAttr(Long.valueOf(jobId), stepCode);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/step")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "????????????????????????", notes = "????????????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<ResponseVO> saveJobStepInfo(@RequestBody Map<String, Object> stepAttrMap) {
        if (isStepAttrMapValid(stepAttrMap)) {
            Long jobId = Long.valueOf(stepAttrMap.get(Constants.JOB_ID).toString());
            String stepCode = stepAttrMap.get(Constants.JOB_STEP_CODE).toString();
            String jobGraphStr = toJsonStr(stepAttrMap.get(Constants.JOB_GRAPH));
            Map<String, List<JobGraphVO>> map = JSONUtil.toBean(jobGraphStr,
                    new TypeReference<Map<String, List<JobGraphVO>>>() {
                    }, false);
            saveJobGraph(map, jobId);
            if (stepAttrMap.containsKey(Constants.JOB_STEP_TITLE)
                    && StrUtil.isNotEmpty(stepAttrMap.get(Constants.JOB_STEP_TITLE).toString())) {
                DiJobStepDTO step = new DiJobStepDTO();
                step.setJobId(jobId);
                step.setStepCode(stepCode);
                step.setStepTitle(stepAttrMap.get(Constants.JOB_STEP_TITLE).toString());
                this.diJobStepService.update(step);
            }
            DiJobStepDTO dto = this.diJobStepService.selectOne(jobId, stepCode);
            if (dto != null) {
                List<DiJobStepAttrTypeDTO> attrTypeList = this.diJobStepAttrTypeService.listByType(dto.getStepType().getValue(), dto.getStepName());
                for (DiJobStepAttrTypeDTO attrType : attrTypeList) {
                    if (stepAttrMap.containsKey(attrType.getStepAttrKey())) {
                        DiJobStepAttrDTO stepAttr = new DiJobStepAttrDTO();
                        stepAttr.setJobId(jobId);
                        stepAttr.setStepCode(stepCode);
                        stepAttr.setStepAttrKey(attrType.getStepAttrKey());
                        stepAttr.setStepAttrValue(toJsonStr(stepAttrMap.get(attrType.getStepAttrKey())));
                        this.diJobStepAttrService.upsert(stepAttr);
                    } else {
                        DiJobStepAttrDTO stepAttr = new DiJobStepAttrDTO();
                        stepAttr.setJobId(jobId);
                        stepAttr.setStepCode(stepCode);
                        stepAttr.setStepAttrKey(attrType.getStepAttrKey());
                        stepAttr.setStepAttrValue(attrType.getStepAttrDefaultValue());
                        this.diJobStepAttrService.upsert(stepAttr);
                    }
                }
            }
        }
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    private String toJsonStr(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return String.valueOf(obj);
        }
        return JSONUtil.toJsonStr(obj);
    }

    /**
     * ??????????????????????????????????????????????????????JOB_ID JOB_STEP_CODE JOB_GRAPH
     *
     * @param stepAttrMap map
     * @return boolean
     */
    private boolean isStepAttrMapValid(Map<String, Object> stepAttrMap) {
        if (CollectionUtil.isEmpty(stepAttrMap)) {
            return false;
        }
        return stepAttrMap.containsKey(Constants.JOB_ID)
                && StrUtil.isNotEmpty(toJsonStr(stepAttrMap.get(Constants.JOB_ID)))
                && stepAttrMap.containsKey(Constants.JOB_STEP_CODE)
                && StrUtil.isNotEmpty(toJsonStr(stepAttrMap.get(Constants.JOB_STEP_CODE)))
                && stepAttrMap.containsKey(Constants.JOB_GRAPH)
                && StrUtil.isNotEmpty(toJsonStr(stepAttrMap.get(Constants.JOB_GRAPH)))
                ;
    }

    @Logging
    @GetMapping(path = "/publish/{jobId}")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "????????????", notes = "????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<ResponseVO> publishJob(@PathVariable(value = "jobId") Long jobId) {
        DiJobDTO jobInfo = this.diJobService.selectOne(jobId);
        if (JobStatusEnum.ARCHIVE.getValue().equals(jobInfo.getJobStatus().getValue())) {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                    I18nUtil.get("response.error.di.archivedJob"), ErrorShowTypeEnum.NOTIFICATION), HttpStatus.OK);
        }
        if (JobRuntimeStateEnum.STOP.getValue().equals(jobInfo.getRuntimeState().getValue())) {
            DiJobDTO job = new DiJobDTO();
            job.setId(jobId);
            job.setJobStatus(DictVO.toVO(DictConstants.JOB_STATUS, JobStatusEnum.RELEASE.getValue()));
            this.diJobService.update(job);
            this.diJobService.archive(jobInfo.getJobCode(), jobInfo.getDirectory().getId());
            return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                    I18nUtil.get("response.error.di.publishJob"), ErrorShowTypeEnum.NOTIFICATION), HttpStatus.OK);
        }
    }

    @Logging
    @PostMapping(path = "/run")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "????????????", notes = "??????????????????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<ResponseVO> runJob(@RequestBody DiJobRunVO jobRunParam) throws Exception {
        //config cluster and resource
        this.diJobService.update(jobRunParam.toDto());
        this.diJobResourceFileService.bindResource(jobRunParam.getJobId(), jobRunParam.getResources());
        //generate json file
        DiJobDTO job = queryJobInfo(jobRunParam.getJobId());
        String jobJson = jobConfigHelper.buildJob(job);
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File baseDir = FileUtil.mkdir(tempDir.getAbsolutePath() + File.separator + job.getProjectId());
        File tmpJobConfFile = FileUtil.file(baseDir, job.getJobCode() + ".json");
        FileUtil.writeUtf8String(jobJson, tmpJobConfFile);
        String seatunnelPath = this.systemConfigService.getSeatunnelHome();
        Path seatunnelJarPath = Paths.get(seatunnelPath, "lib", "seatunnel-core-flink.jar");
        if (StrUtil.isBlank(seatunnelPath)) {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                    I18nUtil.get("response.error.di.noJar.seatunnel"), ErrorShowTypeEnum.NOTIFICATION), HttpStatus.OK);
        }
        CliClient client = new DescriptorCliClient();
        //build configuration
        DiClusterConfigDTO clusterConfig = this.diClusterConfigService.selectOne(jobRunParam.getClusterId());
        Configuration configuration = buildConfiguration(seatunnelJarPath, job.getId(), clusterConfig.getConfig(), baseDir);
        //build job
        PackageJarJob jarJob = buildJob(seatunnelJarPath.toUri().toString(), tmpJobConfFile, job.getJobAttrList());
        JobID jobID = client.submit(DeploymentTarget.STANDALONE_SESSION, configuration, jarJob);
        job.setRuntimeState(DictVO.toVO(DictConstants.RUNTIME_STATE, JobRuntimeStateEnum.RUNNING.getValue()));
        this.diJobService.update(job);

        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/resource/{jobId}")
    @ApiOperation(value = "??????????????????", notes = "????????????????????????")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_JOB_EDIT)")
    public ResponseEntity<List<DictVO>> listJobResourceFile(@PathVariable(value = "jobId") Long jobId) {
        List<DictVO> list = new ArrayList<>();
        List<DiResourceFileDTO> resourceList = this.diJobResourceFileService.listJobResources(jobId);
        for (DiResourceFileDTO dto : resourceList) {
            DictVO dict = new DictVO(String.valueOf(dto.getId()), dto.getFileName());
            list.add(dict);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private Configuration buildConfiguration(Path seatunnelJarPath, Long jobId, Map<String, String> clusterConf, File baseDir) throws MalformedURLException {
        Configuration configuration = new Configuration();
        configuration.setString(JobManagerOptions.ADDRESS, clusterConf.get(JobManagerOptions.ADDRESS.key()));
        configuration.setInteger(JobManagerOptions.PORT, Integer.parseInt(clusterConf.get(JobManagerOptions.PORT.key())));
        configuration.setInteger(RestOptions.PORT, Integer.parseInt(clusterConf.get(RestOptions.PORT.key())));
        List<DiResourceFileDTO> resourceList = this.diJobResourceFileService.listJobResources(jobId);
        List<String> jars = new ArrayList<>();
        jars.add(seatunnelJarPath.toUri().toString());
        StorageService localStorageService = new NioFileServiceImpl(baseDir.getAbsolutePath());
        for (DiResourceFileDTO file : resourceList) {
            Long fileSize = this.storageService.getFileSize(file.getFilePath(), file.getFileName());
            if (localStorageService.exists(file.getFileName()) && fileSize.equals(localStorageService.getFileSize("", file.getFileName()))) {
                File localFile = FileUtil.file(baseDir, file.getFileName());
                jars.add(localFile.toURI().toString());
            } else {
                InputStream is = this.storageService.get(file.getFilePath(), file.getFileName());
                File localFile = FileUtil.file(baseDir, file.getFileName());
                FileUtil.writeFromStream(is, localFile);
                jars.add(localFile.toURI().toString());
            }
        }
        ConfigUtils.encodeCollectionToConfig(configuration, PipelineOptions.JARS, jars, Object::toString);
        configuration.setString(DeploymentOptions.TARGET, RemoteExecutor.NAME);
        return configuration;
    }

    private PackageJarJob buildJob(String seatunnelPath, File file, List<DiJobAttrDTO> jobAttrList) throws FileNotFoundException, MalformedURLException {
        PackageJarJob jarJob = new PackageJarJob();
        jarJob.setJarFilePath(seatunnelPath);
        jarJob.setEntryPointClass("org.apache.seatunnel.SeatunnelFlink");
        URL resource = ResourceUtils.getFile(file.toURI()).toURI().toURL();
        List<String> variables = new ArrayList<String>() {{
            add("--config");
            add(resource.getPath());
        }};
        jobAttrList.stream()
                .filter(attr -> JobAttrTypeEnum.JOB_ATTR.getValue().equals(attr.getJobAttrType().getValue()))
                .forEach(attr -> {
                    variables.add("--variable");
                    variables.add(attr.getJobAttrKey() + "=" + attr.getJobAttrValue());
                });
        jarJob.setProgramArgs(variables.toArray(new String[0]));
        jarJob.setClasspaths(Collections.emptyList());
        jarJob.setSavepointSettings(SavepointRestoreSettings.none());
        return jarJob;
    }

    private DiJobDTO queryJobInfo(Long jobId) {
        DiJobDTO job = this.diJobService.selectOne(jobId);
        List<DiJobAttrDTO> jobAttrList = this.diJobAttrService.listJobAttr(jobId);
        List<DiJobLinkDTO> jobLinkList = this.diJobLinkService.listJobLink(jobId);
        List<DiJobStepDTO> jobStepList = this.diJobStepService.listJobStep(jobId);
        job.setJobAttrList(jobAttrList);
        job.setJobLinkList(jobLinkList);
        job.setJobStepList(jobStepList);
        return job;
    }

}