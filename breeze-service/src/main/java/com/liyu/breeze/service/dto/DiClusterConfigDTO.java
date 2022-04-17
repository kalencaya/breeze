package com.liyu.breeze.service.dto;

import com.liyu.breeze.service.vo.DictVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @ApiModelProperty(value = "集群名称")
    private String clusterName;

    @ApiModelProperty(value = "集群类型")
    private DictVO clusterType;

    @ApiModelProperty(value = "集群home文件目录地址")
    private String clusterHome;

    @ApiModelProperty(value = "集群版本")
    private String clusterVersion;

    @ApiModelProperty(value = "配置信息json格式")
    private String clusterConf;

    @ApiModelProperty(value = "备注")
    private String remark;


}
