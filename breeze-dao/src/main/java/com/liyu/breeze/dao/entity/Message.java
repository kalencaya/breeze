package com.liyu.breeze.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 站内信表
 * </p>
 *
 * @author liyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_message")
@ApiModel(value = "Message对象", description = "站内信表")
public class Message extends BaseDO {

    private static final long serialVersionUID = 1569135129606430763L;

    @ApiModelProperty(value = "消息内容id")
    private Long messageTextId;

    @ApiModelProperty(value = "收件人")
    private String receiver;

    @ApiModelProperty(value = "是否已读")
    private String isRead;

    @ApiModelProperty(value = "是否删除")
    private String isDelete;


}
