package com.liyu.breeze.service.dto;

import com.liyu.breeze.service.vo.DictVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lenovo
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TableColumnMetaDTO extends BaseDTO {
    private static final long serialVersionUID = -6282945619070309858L;
    private Long id;

    private Long tableId;

    private String columnName;

    private String dataType;

    private Long dataLength;

    private Integer dataPrecision;

    private Integer dataScale;

    private DictVO nullable;

    private String dataDefault;

    private String lowValue;

    private String highValue;

    private Integer columnOrdinal;

    private String columnComment;

    private DictVO isPrimaryKey;
}
