package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 发送文本消息请求DTO
 */
@Data
@ApiModel("发送文本消息请求")
public class MessageSendDTO {

    @ApiModelProperty(value = "商品ID", required = true)
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    @ApiModelProperty(value = "发送者ID", required = true)
    @NotNull(message = "发送者ID不能为空")
    private Long senderId;

    @ApiModelProperty(value = "接收者ID", required = true)
    @NotNull(message = "接收者ID不能为空")
    private Long receiverId;

    @ApiModelProperty(value = "消息内容", required = true)
    @NotBlank(message = "消息内容不能为空")
    private String content;
}