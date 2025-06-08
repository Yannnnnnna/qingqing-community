package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * 图片消息发送DTO
 */
@Data
@ApiModel("图片消息发送DTO")
public class ImageSendDTO {

    @ApiModelProperty("商品ID")
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    @ApiModelProperty("发送者ID")
    private Long senderId;

    @ApiModelProperty("接收者ID")
    private Long receiverId;

    @ApiModelProperty("图片文件（用于直接上传）")
    private MultipartFile file;

    @ApiModelProperty("图片URL（用于已上传的图片）")
    private String imageUrl;
}