package com.liyu.breeze.tree;

import cn.hutool.core.io.FileUtil;
import cn.hutool.db.Db;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserCenterTest {
    @Test
    public void simpleTest() throws SQLException {
        List<Dept> list = Db.use().query("select " +
                "         case when parent_id = 0 then null else  concat(company_id,parent_id) end as parentId" +
                "        ,concat(company_id,dept_id) as deptId" +
                "        ,dept_name as deptName" +
                "        ,company_id as companyId" +
                "        ,is_deleted as isDeleted" +
                "    from dept t " +
                "    where is_deleted = 0   ", Dept.class);
        List<Dept> treeList = new ArrayList<>();
        for (Dept dept : list) {
            if (dept.getParentId() == null) {
                treeList.add(dept);
            }
        }
        findChildren(treeList, list);
        log.info("" + treeList.size());
        printDeptId(treeList);

    }
    private void printDeptId(List<Dept> depts) {
        if (depts == null) {
            return;
        }
        for (Dept dept : depts) {
            FileUtil.appendUtf8String("'"+dept.getDeptId()+"',\n", "E:/tmp/depts.txt");
            printDeptId(dept.getChildren());
        }
    }

    private void findChildren(List<Dept> treeList, List<Dept> allList) {
        for (Dept dept : treeList) {
            List<Dept> children = new ArrayList<>();
            for (Dept d : allList) {
                if (d.getParentId() != null && d.getParentId().equals(dept.getDeptId()) && d.getIsDeleted().equals("0")) {
                    children.add(d);
                }
            }
            dept.setChildren(children);
            findChildren(children, allList);
        }
    }

}


//    List<SysDept> sysDepts = new ArrayList<>();
//    List<SysDept> depts = sysDeptMapper.findAll();
//		for (SysDept dept : depts) {
//                if (dept.getParentId() == null || dept.getParentId() == 0) {
//                dept.setLevel(0);
//                sysDepts.add(dept);
//                }
//                }
//                findChildren(sysDepts, depts);
//                return sysDepts


//    private void findChildren(List<SysDept> sysDepts, List<SysDept> depts) {
//        for (SysDept sysDept : sysDepts) {
//            List<SysDept> children = new ArrayList<>();
//            for (SysDept dept : depts) {
//                if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
//                    dept.setParentName(dept.getName());
//                    dept.setLevel(sysDept.getLevel() + 1);
//                    children.add(dept);
//                }
//            }
//            sysDept.setChildren(children);
//            findChildren(children, depts);
//        }


@Data
class Dept {
    private String parentId;
    private String deptId;
    private String deptName;
    private String companyId;
    private List<Dept> children;
    private String isDeleted;
}