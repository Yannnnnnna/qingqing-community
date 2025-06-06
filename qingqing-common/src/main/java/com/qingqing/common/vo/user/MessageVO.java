package com.qingqing.common.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 消息返回视图对象
 */
@Data
@ApiModel("消息信息")
public class MessageVO {

    @ApiModelProperty("消息ID")
    private Long id;

    @ApiModelProperty("商品ID")
    private Long goodsId;

    @ApiModelProperty("商品标题")
    private String goodsTitle;

    @ApiModelProperty("发送者ID")
    private Long senderId;

    @ApiModelProperty("发送者昵称")
    private String senderNickname;


    @ApiModelProperty("接收者ID")
    private Long receiverId;

    @ApiModelProperty("接收者昵称")
    private String receiverNickname;

    @ApiModelProperty("消息类型：0-文本，1-图片")
    private Integer contentType;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}