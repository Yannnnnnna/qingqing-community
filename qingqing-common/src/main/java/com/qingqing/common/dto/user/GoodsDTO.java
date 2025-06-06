package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 二手商品分页查询结果
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("二手商品查询结果")
public class GoodsDTO {
    @ApiModelProperty(value = "商品ID", example = "雪花生成的id")
    private Long id; // 商品ID

    @ApiModelProperty(value = "发布者名字", example = "张三")
    private String publisherName; // 发布者名字

    @ApiModelProperty(value = "封面", example = "返回第一个URL")
    private String image;

    @ApiModelProperty(value = "商品标题", example = "Java编程思想")
    private String title; // 商品标题

    @ApiModelProperty(value = "商品价格", example = "59.9")
    private BigDecimal price; // 商品价格

}

