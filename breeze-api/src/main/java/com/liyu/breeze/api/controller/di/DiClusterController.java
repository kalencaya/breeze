package com.liyu.breeze.api.controller.di;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.vo.ResponseVO;
import com.liyu.breeze.service.di.DiClusterConfigService;
import com.liyu.breeze.service.dto.di.DiClusterConfigDTO;
import com.liyu.breeze.service.param.di.DiClusterConfigParam;
import com.liyu.breeze.service.vo.DictVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author gleiyu
 */

@Slf4j
@Api(tags = "数据集成-集群管理")
@RestController
@RequestMapping(path = "/api/di/cluster")
public class DiClusterController {

    @Autowired
    private DiClusterConfigService diClusterConfigService;

    @Logging
    @GetMapping
    @ApiOperation(value = "查询集群列表", notes = "分页查询集群列表")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_CLUSTER_SELECT)")
    public ResponseEntity<Page<DiClusterConfigDTO>> listCluster(DiClusterConfigParam param) {
        Page<DiClusterConfigDTO> page = this.diClusterConfigService.listByPage(param);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "/all")
    @ApiOperation(value = "查询全部集群", notes = "查询所有集群列表")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_CLUSTER_SELECT)")
    public ResponseEntity<List<DictVO>> listAll() {
        List<DictVO> list = this.diClusterConfigService.listAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Logging
    @PostMapping
    @ApiOperation(value = "新增集群", notes = "新增集群")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_CLUSTER_ADD)")
    public ResponseEntity<ResponseVO> addCluster(@Validated @RequestBody DiClusterConfigDTO diClusterConfigDTO) {
        //todo 检查集群配置信息，必要属性check
        this.diClusterConfigService.insert(diClusterConfigDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.CREATED);
    }

    @Logging
    @PutMapping
    @ApiOperation(value = "修改集群", notes = "修改集群")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_CLUSTER_EDIT)")
    public ResponseEntity<ResponseVO> editCluster(@Validated @RequestBody DiClusterConfigDTO diClusterConfigDTO) {
        this.diClusterConfigService.update(diClusterConfigDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "删除集群", notes = "删除集群")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_CLUSTER_DELETE)")
    public ResponseEntity<ResponseVO> deleteCluster(@PathVariable(value = "id") Long id) {
        //todo 检查集群是否有任务在运行
        this.diClusterConfigService.deleteById(id);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @PostMapping(path = "/batch")
    @ApiOperation(value = "批量删除集群", notes = "批量删除集群")
    @PreAuthorize("@svs.validate(T(com.liyu.breeze.common.constant.PrivilegeConstants).STUDIO_CLUSTER_DELETE)")
    public ResponseEntity<ResponseVO> deleteCluster(@RequestBody Map<Integer, Long> map) {
        //todo 检查集群是否有任务在运行
        this.diClusterConfigService.deleteBatch(map);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }
}
