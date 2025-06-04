package com.qingqing.common.dto.admin.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品分类添加
 *
 * @author wyr on 2025/6/3
 */
@ApiModel("商品分类添加模型")
@Data
public class CategoryAddDTO {
    //分类名称
    @ApiModelProperty(value = "分类名称", example = "家用电器")
    private String name;
    //分类状态
    @ApiModelProperty(value = "分类状态", example = "1", notes = "0: 正常, 1: 禁用")
    private Integer status; // 0: 正常, 1: 禁用
    //分类排序
    @ApiModelProperty(value = "分类排序", example = "1", notes = "数字越小，排序越靠前")
    private Integer sort;
}
