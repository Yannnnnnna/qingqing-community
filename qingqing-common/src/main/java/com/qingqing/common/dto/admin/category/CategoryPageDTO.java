package com.qingqing.common.dto.admin.category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商品分类分页查询结果
 *
 * @author wyr on 2025/6/3
 */
@ApiModel("商品分类管查询结果")
@Data
public class CategoryPageDTO {
    @ApiModelProperty(value = "分类ID", example = "1001")
    private Long id;

    @ApiModelProperty(value = "分类名称", example = "家用电器")
    private String name; // 分类名称

    @ApiModelProperty(value = "排序字段", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "状态 (0: 禁用, 1: 启用)", example = "1")
    private Integer status;

    @ApiModelProperty(value = "创建时间", example = "2025-05-28 19:27:47")
    private LocalDateTime createTime; // 创建时间
}
