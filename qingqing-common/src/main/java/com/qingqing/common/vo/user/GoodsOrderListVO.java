package com.qingqing.common.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel("订单列表VO")
public class GoodsOrderListVO {
    @ApiModelProperty(value = "订单ID", example = "1790123456789012011")
    private Long id;

    @ApiModelProperty(value = "商品ID", example = "1786543210987654322")
    private Long goodsId;

    @ApiModelProperty(value = "商品标题", example = "二手手机出售")
    private String goodsTitle;

    @ApiModelProperty(value = "商品封面图片", example = "http://localhost:8080/imgs/goods/uuid.jpg")
    private String goodsCoverImage;

    @ApiModelProperty(value = "对方用户ID", example = "12345")
    private Long otherUserId;

    @ApiModelProperty(value = "对方用户昵称", example = "小明")
    private String otherUserName;

    @ApiModelProperty(value = "成交价格", example = "1999.99")
    private BigDecimal price;

    @ApiModelProperty(value = "订单状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "订单状态描述", example = "已支付")
    private String statusDesc;

    @ApiModelProperty(value = "创建时间", example = "2025-06-06 10:30:00")
    private LocalDateTime createTime;
}
