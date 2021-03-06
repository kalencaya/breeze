package com.liyu.breeze.service.dto.di;

import com.liyu.breeze.service.dto.BaseDTO;
import com.liyu.breeze.service.vo.DictVO;
import com.liyu.breeze.service.vo.JobGraphVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据集成-作业信息
 * </p>
 *
 * @author liyu
 * @since 2022-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "作业信息", description = "数据集成-作业信息")
public class DiJobDTO extends BaseDTO {

    private static final long serialVersionUID = -4161534628783250968L;

    @NotNull
    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @NotBlank
    @Length(min = 1, max = 120)
    @Pattern(regexp = "\\w+$")
    @ApiModelProperty(value = "作业编码")
    private String jobCode;

    @NotBlank
    @Length(min = 1, max = 200)
    @ApiModelProperty(value = "作业名称")
    private String jobName;

    @NotNull
    @ApiModelProperty(value = "作业目录")
    private DiDirectoryDTO directory;

    @ApiModelProperty(value = "作业类型 实时、离线")
    private DictVO jobType;

    @Length(max = 32)
    @ApiModelProperty(value = "负责人")
    private String jobOwner;

    @ApiModelProperty(value = "作业状态 草稿、发布、归档")
    private DictVO jobStatus;

    @ApiModelProperty(value = "运行状态 停止、运行中、等待运行")
    private DictVO runtimeState;

    @ApiModelProperty(value = "作业版本号")
    private Integer jobVersion;

    @ApiModelProperty(value = "集群id")
    private Long clusterId;

    @Length(max = 200)
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "作业属性信息")
    private List<DiJobAttrDTO> jobAttrList;

    @ApiModelProperty(value = "作业连线信息")
    private List<DiJobLinkDTO> jobLinkList;

    @ApiModelProperty(value = "步骤信息")
    private List<DiJobStepDTO> jobStepList;

    @ApiModelProperty(value = "作业图信息")
    private Map<String, List<JobGraphVO>> jobGraph;
}
