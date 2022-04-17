package com.liyu.breeze.api.controller.di;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.util.I18nUtil;
import com.liyu.breeze.api.vo.ResponseVO;
import com.liyu.breeze.common.enums.ErrorShowTypeEnum;
import com.liyu.breeze.common.enums.ResponseCodeEnum;
import com.liyu.breeze.service.di.DiJobService;
import com.liyu.breeze.service.di.DiProjectService;
import com.liyu.breeze.service.dto.DiProjectDTO;
import com.liyu.breeze.service.param.DiProjectParam;
import com.liyu.breeze.service.vo.DictVO;
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
import java.util.List;
import java.util.Map;

/**
 * @author gleiyu
 */
@Slf4j
@Api(tags = "数据集成-项目管理")
@RestController
@RequestMapping(path = "/api/di/project")
public class DiProjectController {
    @Autowired
    private DiProjectService diProjectService;

    @Autowired
    private DiJobService diJobService;

    @Logging
    @GetMapping
    @ApiOperation(value = "查询项目列表", notes = "分页查询项目列表")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_PROJECT_SELECT)")
    public ResponseEntity<Page<DiProjectDTO>> listProject(DiProjectParam param) {
        Page<DiProjectDTO> page = this.diProjectService.listByPage(param);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/all")
    @ApiOperation(value = "查询所有项目列表", notes = "查询所有项目列表")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_PROJECT_SELECT)")
    public ResponseEntity<List<DictVO>> listAll() {
        List<DictVO> result = new ArrayList<>();
        List<DiProjectDTO> list = this.diProjectService.listAll();
        list.forEach(p -> {
            result.add(new DictVO(String.valueOf(p.getId()), p.getProjectCode()));
        });
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Logging
    @PostMapping
    @ApiOperation(value = "新增项目", notes = "新增项目")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_PROJECT_ADD)")
    public ResponseEntity<ResponseVO> addProject(@Validated @RequestBody DiProjectDTO diProjectDTO) {
        this.diProjectService.insert(diProjectDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }

    @Logging
    @PutMapping
    @ApiOperation(value = "修改项目", notes = "修改项目")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_PROJECT_EDIT)")
    public ResponseEntity<ResponseVO> editProject(@Validated @RequestBody DiProjectDTO diProjectDTO) {
        this.diProjectService.update(diProjectDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @DeleteMapping(path = "/{id}")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "删除项目", notes = "删除项目")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_PROJECT_DELETE)")
    public ResponseEntity<ResponseVO> deleteProject(@PathVariable(value = "id") Long projectId) {
        List<Long> projectids = new ArrayList<Long>() {{
            add(projectId);
        }};
        if (this.diJobService.hasValidJob(projectids)) {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                    I18nUtil.get("response.error.di.notEmptyProject"), ErrorShowTypeEnum.NOTIFICATION), HttpStatus.OK);
        } else {
            this.diProjectService.deleteById(projectId);
            return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
        }
    }

    @Logging
    @PostMapping(path = "/batch")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "批量删除项目", notes = "批量删除项目")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_PROJECT_DELETE)")
    public ResponseEntity<ResponseVO> deleteProject(@RequestBody Map<Integer, Long> map) {
        if (this.diJobService.hasValidJob(map.values())) {
            return new ResponseEntity<>(ResponseVO.error(ResponseCodeEnum.ERROR_CUSTOM.getCode(),
                    I18nUtil.get("response.error.di.notEmptyProject"), ErrorShowTypeEnum.NOTIFICATION), HttpStatus.OK);
        } else {
            this.diProjectService.deleteBatch(map);
            return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
        }
    }
}
