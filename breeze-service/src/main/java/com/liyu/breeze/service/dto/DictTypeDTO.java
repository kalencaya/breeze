package com.liyu.breeze.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 数据字典类型
 * </p>
 *
 * @author liyu
 * @since 2021-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DictType对象", description = "数据字典类型")
public class DictTypeDTO extends BaseDTO {

    private static final long serialVersionUID = -4879123775050423963L;

    @NotBlank
    @Length(min = 1, max = 30)
    @Pattern(regexp = "\\w+$")
    @ApiModelProperty(value = "字典类型编码")
    private String dictTypeCode;

    @NotBlank
    @Length(min = 1, max = 100)
    @ApiModelProperty(value = "字典类型名称")
    private String dictTypeName;

    @Length(max = 200)
    @ApiModelProperty(value = "备注")
    private String remark;


}
