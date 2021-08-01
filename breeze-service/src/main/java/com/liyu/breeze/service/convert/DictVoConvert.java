package com.liyu.breeze.service.convert;

import com.liyu.breeze.service.vo.DictVO;
import org.mapstruct.Mapper;

/**
 * @author gleiyu
 */
@Mapper
public interface DictVoConvert {
    default String toDo(DictVO vo) {
        if (vo == null) {
            return null;
        }
        return vo.getValue();
    }
}
