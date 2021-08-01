package com.liyu.breeze.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 站内信内容表
 * </p>
 *
 * @author liyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_message_text")
@ApiModel(value = "MessageText对象", description = "站内信内容表")
public class MessageText extends BaseDO {

    private static final long serialVersionUID = 1833210289960772832L;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "消息类型")
    private String messageType;

    @ApiModelProperty(value = "发送人")
    private String sender;

    @ApiModelProperty(value = "内容")
    private String content;


}
