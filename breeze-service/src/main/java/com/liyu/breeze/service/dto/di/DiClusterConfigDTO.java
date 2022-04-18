package com.liyu.breeze.service.dto.di;

import com.liyu.breeze.service.dto.BaseDTO;
import com.liyu.breeze.service.vo.DictVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 数据集成-集群配置
 * </p>
 *
 * @author liyu
 * @since 2022-04-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DiClusterConfig对象", description = "数据集成-集群配置")
public class DiClusterConfigDTO extends BaseDTO {

    private static final long serialVersionUID = -7439123852299668659L;

    @NotBlank
    @Length(max = 128)
    @ApiModelProperty(value = "集群名称")
    private String clusterName;

    @NotNull
    @ApiModelProperty(value = "集群类型")
    private DictVO clusterType;

    @ApiModelProperty(value = "集群home文件目录地址")
    private String clusterHome;

    @ApiModelProperty(value = "集群版本")
    private String clusterVersion;

    @NotBlank
    @ApiModelProperty(value = "配置信息json格式")
    private String clusterConf;

    @ApiModelProperty(value = "备注")
    private String remark;


}
