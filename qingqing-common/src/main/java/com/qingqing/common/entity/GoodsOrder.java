package com.qingqing.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品订单表
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="GoodsOrder对象", description="商品订单表")
public class GoodsOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "商品ID")
    private Long goodsId;

    @ApiModelProperty(value = "买家ID")
    private Long buyerId;

    @ApiModelProperty(value = "卖家ID")
    private Long sellerId;

    @ApiModelProperty(value = "成交价格")
    private BigDecimal price;

    @ApiModelProperty(value = "状态(0-待支付,1-已支付,2-已发货,3-已完成,4-已取消)")
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
