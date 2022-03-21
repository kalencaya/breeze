package com.liyu.breeze.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyu.breeze.ApplicationTest;
import com.liyu.breeze.service.DictService;
import com.liyu.breeze.service.dto.DictDTO;
import com.liyu.breeze.service.dto.DictTypeDTO;
import com.liyu.breeze.service.param.DictParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;

import java.util.List;

class DictServiceImplTest extends ApplicationTest {

    @Autowired
    private DictService dictService;

    @Autowired
    @Qualifier(value = "unBoundedCacheManager")
    private CacheManager cacheManager;

    @Test
    void simpleTest() {
        DictDTO dto = new DictDTO();

        DictTypeDTO typeDTO = new DictTypeDTO();
        typeDTO.setDictTypeCode("2");
        typeDTO.setDictTypeName("name");
        dto.setDictType(typeDTO);
        dto.setDictCode("hello3");
        dto.setDictValue("world");
        this.dictService.insert(dto);
        List<DictDTO> list = this.dictService.selectByType("2");
        for (DictDTO d : list) {
            log.info(d.getDictValue());
        }
    }

    @Test
    void deleteByTypeTest() {
        this.dictService.deleteByType("3");
    }

    @Test
    void listByPageTest() {
        DictParam param = new DictParam();
        param.setCurrent(1L);
        param.setPageSize(20L);
        Page<DictDTO> list = this.dictService.listByPage(param);
        for (DictDTO d : list.getRecords()) {
            log.info(d.getDictValue());
        }
    }
    
}
