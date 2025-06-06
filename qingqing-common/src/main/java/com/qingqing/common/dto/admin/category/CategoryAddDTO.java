package com.qingqing.common.dto.admin.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 商品分类添加
 *
 * @author wyr on 2025/6/3
 */
@ApiModel("商品分类添加模型")
@Data
public class CategoryAddDTO {

    /**
     * 分类名称（必填，最大长度50）
     */
    @NotBlank(message = "分类名称不能为空")
    @Size(max = 50, message = "分类名称不能超过50个字符")
    @ApiModelProperty(value = "分类名称", example = "家用电器", required = true)
    private String name;

    /**
     * 分类状态（只能是0或1）
     */
    @NotNull(message = "分类状态不能为空")
    @Min(value = 0, message = "状态必须为0或1")
    @Max(value = 1, message = "状态必须为0或1")
    @ApiModelProperty(value = "分类状态", example = "1", notes = "0: 正常, 1: 禁用", required = true)
    private Integer status;

    /**
     * 分类排序（必须为非负整数）
     */
    @NotNull(message = "分类排序不能为空")
    @Min(value = 0, message = "排序值必须大于等于0")
    @ApiModelProperty(value = "分类排序", example = "1", notes = "数字越小，排序越靠前")
    private Integer sort;
}
