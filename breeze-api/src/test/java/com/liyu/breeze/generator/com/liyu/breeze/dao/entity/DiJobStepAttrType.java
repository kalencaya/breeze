package com.liyu.breeze.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.liyu.breeze.dao.entity.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据集成-作业步骤参数类型信息
 * </p>
 *
 * @author liyu
 * @since 2022-03-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("di_job_step_attr_type")
@ApiModel(value="DiJobStepAttrType对象", description="数据集成-作业步骤参数类型信息")
public class DiJobStepAttrType extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "步骤类型")
    private String stepType;

    @ApiModelProperty(value = "步骤名称")
    private String stepName;

    @ApiModelProperty(value = "步骤参数key")
    private String stepAttrKey;

    @ApiModelProperty(value = "步骤参数默认值")
    private String stepAttrDefaultValue;

    @ApiModelProperty(value = "是否需要")
    private String isRequired;

    @ApiModelProperty(value = "步骤参数描述")
    private String stepAttrDescribe;


}
