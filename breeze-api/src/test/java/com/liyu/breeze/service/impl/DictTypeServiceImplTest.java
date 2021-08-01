package com.liyu.breeze.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.BreezeApplication;
import com.liyu.breeze.service.DictTypeService;
import com.liyu.breeze.service.dto.DictTypeDTO;
import com.liyu.breeze.service.param.DictTypeParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest(classes = BreezeApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class DictTypeServiceImplTest {

    @Autowired
    private DictTypeService dictTypeService;

    @Test
    public void simpleTest() {
        DictTypeDTO dto = new DictTypeDTO();
        dto.setDictTypeCode("test_code");
        dto.setDictTypeName("name");
        dto.setRemark("备注");
        dto.setCreator("test");
        dto.setEditor("test");
        dto.setUpdateTime(new Date());
        dto.setCreateTime(new Date());
        this.dictTypeService.insert(dto);
        DictTypeDTO dto2 = this.dictTypeService.selectOne("test_code");
        dto2.setRemark("修改");
        this.dictTypeService.update(dto2);
        this.dictTypeService.deleteById(dto2.getId());
    }

    @Test
    public void deleteBatchTest() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 11);
        map.put(2, 12);
        map.put(3, 13);
        this.dictTypeService.deleteBatch(map);
    }

    @Test
    public void listByPageTest() {
//        DictTypeParam param = new DictTypeParam();
//        param.setPageNum(null);
//        param.setPageSize(null);
//        param.setDictTypeCode(null);
        List<DictTypeDTO> list = this.dictTypeService.selectAll();
        for (DictTypeDTO dto : list) {
            log.info(dto.getDictTypeCode());
        }
    }
}
