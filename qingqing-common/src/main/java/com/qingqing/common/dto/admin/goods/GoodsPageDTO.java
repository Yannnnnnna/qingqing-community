package com.qingqing.common.dto.admin.goods;

import com.qingqing.common.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 二手商品分页查询结果
 *
 * @author wyr on 2025/6/3
 */

/**
 * 主键
 * 发布者名字
 * 类别名
 * 标题
 * 价格
 * 状态
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 二手商品分页查询结果
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("二手商品分页查询结果")
public class GoodsPageDTO {
    @ApiModelProperty(value = "商品ID", example = "雪花生成的id")
    private Long id; // 商品ID

    @ApiModelProperty(value = "发布者名字", example = "张三")
    private String publisherName; // 发布者名字

    @ApiModelProperty(value = "类别名", example = "书籍")
    private String categoryName; // 类别名

    @ApiModelProperty(value = "商品标题", example = "Java编程思想")
    private String title; // 商品标题

    @ApiModelProperty(value = "商品价格", example = "59.9")
    private Double price; // 商品价格

    @ApiModelProperty(value = "商品状态 (0: 未审核, 1: 审核通过, 2: 已下架, 3: 已售出)", example = "1")
    private Integer status; // 商品状态 (0: 未审核, 1: 审核通过, 2: 已下架, 3: 已售出)

    @ApiModelProperty(value = "创建时间", example = "2025-06-03 10:00:00")
    private LocalDateTime createTime; // 创建时间
}

