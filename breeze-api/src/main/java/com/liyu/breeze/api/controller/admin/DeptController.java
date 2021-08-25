package com.liyu.breeze.api.controller.admin;


import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.vo.ResponseVO;
import com.liyu.breeze.service.DeptService;
import com.liyu.breeze.service.dto.DeptDTO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.list.TreeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author liyu
 */
@RestController
@RequestMapping("/api/admin/dept")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @Logging
    @GetMapping
    @ApiOperation(value = "查询部门树", notes = "查询部门树")
    public ResponseEntity<List<Tree<Long>>> listDpet() {
        List<DeptDTO> list = this.deptService.listAll();
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("deptId");
        treeNodeConfig.setParentIdKey("pid");
        treeNodeConfig.setNameKey("deptName");
        treeNodeConfig.setWeightKey("deptCode");
        List<Tree<Long>> treeList = TreeUtil.build(list, 0L, treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getPid());
            tree.setName(treeNode.getDeptName());
            tree.setWeight(treeNode.getDeptCode());
        });
        return new ResponseEntity<>(treeList, HttpStatus.OK);
    }


    @Logging
    @GetMapping(path = "/{pid}")
    @ApiOperation(value = "查询子节点部门树", notes = "查询子节点部门树")
    public ResponseEntity<List<Tree<Long>>> listChildDept(@PathVariable(value = "pid") String pid) {
        return new ResponseEntity<>(selectChilds(pid), HttpStatus.OK);
    }

    private List<Tree<Long>> selectChilds(String pid) {
        List<DeptDTO> list = this.deptService.listAll();
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("deptId");
        treeNodeConfig.setParentIdKey("pid");
        treeNodeConfig.setNameKey("deptName");
        treeNodeConfig.setWeightKey("deptCode");
        List<Tree<Long>> treeList = TreeUtil.build(list, Long.valueOf(pid), treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getPid());
            tree.setName(treeNode.getDeptName());
            tree.setWeight(treeNode.getDeptCode());
        });
        return treeList;
    }

    @Logging
    @PostMapping
    @ApiOperation(value = "新增部门", notes = "新增部门")
    public ResponseEntity<ResponseVO> addDept(@Validated @RequestBody DeptDTO deptDTO) {
        this.deptService.insert(deptDTO);
        DeptDTO dept = this.deptService.selectOne(deptDTO.getDeptCode());
        return new ResponseEntity<>(ResponseVO.sucess(dept.getId()), HttpStatus.OK);
    }

    @Logging
    @PutMapping
    @ApiOperation(value = "修改部门", notes = "修改部门")
    public ResponseEntity<ResponseVO> editDept(@Validated @RequestBody DeptDTO deptDTO) {
        this.deptService.update(deptDTO);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @DeleteMapping(path = "/{id}")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "删除部门", notes = "删除部门")
    public ResponseEntity<ResponseVO> deleteDept(@PathVariable(value = "id") String id) {
        List<Tree<Long>> treeList = this.selectChilds(id);
        List<Long> list = new TreeList();
        list.add(Long.valueOf(id));
        getDeptIds(list, treeList);
        this.deptService.deleteBatch(list);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    private void getDeptIds(List<Long> list, List<Tree<Long>> treeList) {
        if (CollectionUtils.isEmpty(treeList)) {
            return;
        }
        for (Tree<Long> tree : treeList) {
            list.add(tree.getId());
            getDeptIds(list, tree.getChildren());
        }
    }
}

