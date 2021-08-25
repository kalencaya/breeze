package com.liyu.breeze.api.controller;

import com.liyu.breeze.BreezeApplication;
import com.liyu.breeze.api.controller.admin.DeptController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest(classes = BreezeApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class DeptControllerTest {

    @Autowired
    private DeptController deptController;

    @Test
    public void TreeBuildTest(){
        this.deptController.listDpet();
    }
}
