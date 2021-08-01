package com.liyu.breeze.dao.mapper;

import com.liyu.breeze.BreezeApplication;
import com.liyu.breeze.dao.entity.DictType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@SpringBootTest(classes = BreezeApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class DictTypeMapperTest {

    @Autowired
    private DictTypeMapper dictTypeMapper;

    @Test
    public void simpleTest() {
        List<DictType> dictTypeList = this.dictTypeMapper.selectList(null);
        for (DictType type : dictTypeList) {
            log.info(type.toString());
        }
    }

//    @Test
//    public void test() {
//        DictType dict = this.dictTypeMapper.xmlSelect();
//        log.info(dict.toString());
//    }
//
//    @Test
//    public void test2() {
//        DictType dict = this.logMapper.xmlSelect();
//        log.info(dict.toString());
//    }
}
