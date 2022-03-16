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
 * 数据集成-项目信息
 * </p>
 *
 * @author liyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DiProject对象", description = "数据集成-项目信息")
public class DiProjectDTO extends BaseDTO {

    private static final long serialVersionUID = -4981655449048129521L;

    @NotBlank
    @Length(min = 1, max = 30)
    @Pattern(regexp = "\\w+$")
    @ApiModelProperty(value = "项目编码")
    private String projectCode;

    @NotBlank
    @Length(min = 1, max = 60)
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @Length(max = 200)
    @ApiModelProperty(value = "备注")
    private String remark;


}
