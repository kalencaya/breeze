package com.liyu.breeze.service.dto;

import com.liyu.breeze.service.vo.DictVO;
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
@ApiModel(value = "Message对象", description = "站内信表")
public class MessageDTO extends BaseDTO {

    private static final long serialVersionUID = -4802816346373359731L;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "消息类型")
    private DictVO messageType;

    @ApiModelProperty(value = "收件人")
    private String receiver;

    @ApiModelProperty(value = "发送人")
    private String sender;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "是否已读")
    private DictVO isRead;

    @ApiModelProperty(value = "是否删除")
    private DictVO isDelete;


}
