package com.liyu.breeze.service.dto;

import com.liyu.breeze.service.vo.DictVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 数据集成-作业步骤信息
 * </p>
 *
 * @author liyu
 * @since 2022-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "作业步骤信息", description = "数据集成-作业步骤信息")
public class DiJobStepDTO extends BaseDTO {

    private static final long serialVersionUID = -5718957095121629912L;

    @ApiModelProperty(value = "作业id")
    private Long jobId;

    @ApiModelProperty(value = "步骤编码")
    private String stepCode;

    @ApiModelProperty(value = "步骤类型")
    private DictVO stepType;

    @ApiModelProperty(value = "步骤名称")
    private String stepName;

    @ApiModelProperty(value = "x坐标")
    private Integer positionX;

    @ApiModelProperty(value = "y坐标")
    private Integer positionY;

    @ApiModelProperty(value = "步骤属性信息")
    List<DiJobStepAttrDTO> jobStepAttrList;
}
