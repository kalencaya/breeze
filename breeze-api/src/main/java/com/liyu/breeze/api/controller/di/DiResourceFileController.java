package com.liyu.breeze.api.controller.di;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.util.I18nUtil;
import com.liyu.breeze.api.util.SecurityUtil;
import com.liyu.breeze.api.vo.ResponseVO;
import com.liyu.breeze.common.exception.Rethrower;
import com.liyu.breeze.service.di.DiResourceFileService;
import com.liyu.breeze.service.dto.di.DiResourceFileDTO;
import com.liyu.breeze.service.param.di.DiResourceFileParam;
import com.liyu.breeze.service.storage.StorageService;
import com.liyu.breeze.service.vo.DictVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = "数据集成-资源管理")
@RestController
@RequestMapping(path = "/api/di/resource")
public class DiResourceFileController {

    @Autowired
    private DiResourceFileService diResourceFileService;

    @Resource(name = "${app.resource.type}")
    private StorageService storageService;

    @Logging
    @GetMapping
    @ApiOperation(value = "分页查询资源列表", notes = "分页查询资源列表")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_SELECT)")
    public ResponseEntity<Page<DiResourceFileDTO>> listResource(DiResourceFileParam param) {
        Page<DiResourceFileDTO> page = this.diResourceFileService.listByPage(param);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/project")
    @ApiOperation(value = "查询项目下资源", notes = "查询项目下资源列表")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_SELECT)")
    public ResponseEntity<List<DictVO>> listByProject(@NotNull Long projectId) {
        List<DictVO> list = this.diResourceFileService.listByProjectId(projectId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @Logging
    @PostMapping
    @ApiOperation(value = "新增资源", notes = "新增资源")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_ADD)")
    public ResponseEntity<ResponseVO> addResource(@NotNull @RequestBody DiResourceFileDTO fileDTO) throws IOException {
        DiResourceFileDTO dto = new DiResourceFileDTO();
        dto.setProjectId(fileDTO.getProjectId());
        dto.setFileName(fileDTO.getFileName());
        dto.setFilePath(String.valueOf(fileDTO.getProjectId()));
        dto.resolveFileType(fileDTO.getFileName());
        Long fileSize = this.storageService.getFileSize(dto.getFilePath(), dto.getFileName());
        dto.setFileSize(fileSize);
        this.diResourceFileService.insert(dto);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }

    @Logging
    @PostMapping(path = "/upload")
    @ApiOperation(value = "上传资源文件", notes = "上传资源文件")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_ADD)")
    public ResponseEntity<ResponseVO> uploadResource(@NotNull String projectId, @RequestParam("file") MultipartFile file) throws IOException {
        if (!this.storageService.exists(projectId)) {
            this.storageService.mkdirs(projectId);
        }
        this.storageService.upload(file.getInputStream(), projectId, file.getOriginalFilename());
        return new ResponseEntity<>(ResponseVO.sucess(file.getOriginalFilename()), HttpStatus.CREATED);
    }

    @Logging
    @DeleteMapping(path = "/upload")
    @ApiOperation(value = "删除资源文件", notes = "删除资源文件")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_ADD)")
    public ResponseEntity<ResponseVO> deleteResource(@NotNull String projectId, @NotNull String fileName) throws IOException {
        this.storageService.delete(projectId, fileName);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }

    @Logging
    @GetMapping(path = "/download")
    @ApiOperation(value = "下载资源文件", notes = "下载资源文件")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_DOWNLOAD)")
    public void downloadResource(@NotNull String projectId, @NotNull String fileName, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        InputStream is = this.storageService.get(projectId, fileName);
        if (is != null) {
            try (BufferedInputStream bis = new BufferedInputStream(is);
                 BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())
            ) {
                FileCopyUtils.copy(bis, bos);
            } catch (Exception e) {
                Rethrower.throwAs(e);
            }
        }
    }

    @Logging
    @DeleteMapping
    @ApiOperation(value = "删除资源", notes = "删除资源")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_DELETE)")
    public ResponseEntity<ResponseVO> deleteResource(@PathVariable(value = "id") Long id) {
        List<DiResourceFileDTO> list = this.diResourceFileService.listByIds(new ArrayList<Long>() {{
            add(id);
        }});
        this.diResourceFileService.deleteById(id);
        if (CollectionUtil.isNotEmpty(list)) {
            DiResourceFileDTO resource = list.get(0);
            this.storageService.delete(String.valueOf(resource.getProjectId()), resource.getFileName());
        }
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/batch")
    @ApiOperation(value = "批量删除资源", notes = "批量删除资源")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_RESOURCE_DELETE)")
    public ResponseEntity<ResponseVO> deleteResource(@RequestBody Map<Integer, String> map) {
        List<DiResourceFileDTO> list = this.diResourceFileService.listByIds(map.values());
        this.diResourceFileService.deleteBatch(map);
        for (DiResourceFileDTO resource : list) {
            this.storageService.delete(String.valueOf(resource.getProjectId()), resource.getFileName());
        }
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }
}
