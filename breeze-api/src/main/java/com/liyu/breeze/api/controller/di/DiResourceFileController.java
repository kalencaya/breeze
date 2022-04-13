package com.liyu.breeze.api.controller.di;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.vo.ResponseVO;
import com.liyu.breeze.service.di.DiResourceFileService;
import com.liyu.breeze.service.dto.DiResourceFileDTO;
import com.liyu.breeze.service.param.DiResourceFileParam;
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
@Api(tags = "数据集成-资源管理")
@RestController
@RequestMapping(path = "/api/di/resource")
public class DiResourceFileController {

    @Autowired
    private DiResourceFileService diResourceFileService;

    @Logging
    @GetMapping
    @ApiOperation(value = "分页查询资源列表", notes = "分页查询资源列表")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_SELECT)")
    public ResponseEntity<Page<DiResourceFileDTO>> listResource(DiResourceFileParam param) {
        Page<DiResourceFileDTO> page = this.diResourceFileService.listByPage(param);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Logging
    @PostMapping
    @ApiOperation(value = "新增资源", notes = "新增资源")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_ADD)")
    public ResponseEntity<ResponseVO> addResource(@Validated @RequestBody DiResourceFileDTO diResourceFileDTO) {
        this.diResourceFileService.insert(diResourceFileDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }

    @Logging
    @PutMapping
    @ApiOperation(value = "修改资源", notes = "修改资源")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_EDIT)")
    public ResponseEntity<ResponseVO> editResource(@Validated @RequestBody DiResourceFileDTO diResourceFileDTO) {
        this.diResourceFileService.update(diResourceFileDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @DeleteMapping
    @ApiOperation(value = "删除资源", notes = "删除资源")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_DELETE)")
    public ResponseEntity<ResponseVO> deleteResource(@PathVariable(value = "id") Long id) {
        this.diResourceFileService.deleteById(id);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/batch")
    @ApiOperation(value = "批量删除资源", notes = "批量删除资源")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_DELETE)")
    public ResponseEntity<ResponseVO> deleteResource(@RequestBody Map<Integer, String> map) {
        this.diResourceFileService.deleteBatch(map);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }
}
