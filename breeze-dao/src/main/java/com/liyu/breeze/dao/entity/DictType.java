package com.liyu.breeze.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据字典类型
 * </p>
 *
 * @author liyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_dict_type")
@ApiModel(value = "DictType对象", description = "数据字典类型")
public class DictType extends BaseDO {

    private static final long serialVersionUID = -4879123775050423963L;

    @ApiModelProperty(value = "字典类型编码")
    private String dictTypeCode;

    @ApiModelProperty(value = "字典类型名称")
    private String dictTypeName;

    @ApiModelProperty(value = "备注")
    private String remark;


}
