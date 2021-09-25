package com.liyu.breeze.service.dto;

import com.liyu.breeze.service.vo.DictVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 用户登录登出日志
 * </p>
 *
 * @author liyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "LogLogin对象", description = "用户登录登出日志")
public class LogLoginDTO extends BaseDTO {

    private static final long serialVersionUID = 722981712401825463L;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "登录时间")
    private Date loginTime;

    @ApiModelProperty(value = "ip地址")
    private String ipAddress;

    @ApiModelProperty(value = "登录类型 1-登录，2-登出，0-未知")
    private DictVO loginType;

    @ApiModelProperty(value = "客户端信息")
    private String clientInfo;

    @ApiModelProperty(value = "操作系统信息")
    private String osInfo;

    @ApiModelProperty(value = "浏览器信息")
    private String browserInfo;

    @ApiModelProperty(value = "接口执行信息，包含请求结果")
    private String actionInfo;


}
