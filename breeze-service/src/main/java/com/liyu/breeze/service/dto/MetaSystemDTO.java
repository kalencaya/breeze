package com.liyu.breeze.service.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 元数据-业务系统信息
 * </p>
 *
 * @author liyu
 * @since 2022-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MetaSystem对象", description = "元数据-业务系统信息")
public class MetaSystemDTO extends BaseDTO {

    private static final long serialVersionUID = 6943543370024427514L;

    @ApiModelProperty(value = "系统编码")
    private String systemCode;

    @ApiModelProperty(value = "系统名称")
    private String systemName;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "联系人手机号码")
    private String contactsPhone;

    @ApiModelProperty(value = "备注")
    private String remark;


}
