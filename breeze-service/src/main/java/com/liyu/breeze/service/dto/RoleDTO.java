package com.liyu.breeze.service.dto;


import com.liyu.breeze.service.vo.DictVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author gleiyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "角色对象", description = "角色信息表")
public class RoleDTO extends BaseDTO {
    private static final long serialVersionUID = 7604916855534200144L;

    @NotBlank
    @Length(min = 1, max = 30)
    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @NotBlank
    @Length(min = 1, max = 50)
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色类型")
    private DictVO roleType;

    @ApiModelProperty(value = "角色状态")
    private DictVO roleStatus;

    @Length(max = 100)
    @ApiModelProperty(value = "角色备注")
    private String roleDesc;

    @ApiModelProperty(value = "权限信息")
    private List<PrivilegeDTO> privileges;
}
