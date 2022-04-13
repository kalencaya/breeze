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
 * 数据集成-资源
 * </p>
 *
 * @author liyu
 * @since 2022-04-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("di_resource_file")
@ApiModel(value="DiResourceFile对象", description="数据集成-资源")
public class DiResourceFile extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "资源名称")
    private String fileName;

    @ApiModelProperty(value = "资源类型")
    private String fileType;

    @ApiModelProperty(value = "资源路径")
    private String filePath;


}
