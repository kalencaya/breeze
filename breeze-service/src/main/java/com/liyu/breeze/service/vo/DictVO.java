package com.liyu.breeze.service.vo;

import com.liyu.breeze.common.constant.Constants;
import com.liyu.breeze.service.cache.DictCache;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gleiyu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictVO {
    private String value;
    private String label;

    public static DictVO toVO(String dictTypeCode, String dictCode) {
        String dictValue = DictCache.getValueByKey(dictTypeCode + Constants.SEPARATOR + dictCode);
        return new DictVO(dictCode, dictValue);
    }
}
