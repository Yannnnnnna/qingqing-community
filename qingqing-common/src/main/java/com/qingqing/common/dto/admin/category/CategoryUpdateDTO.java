package com.qingqing.common.dto.admin.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品分类添加
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("商品分类更新模型")
public class CategoryUpdateDTO extends CategoryAddDTO {
    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID", example = "雪花id")
    private Long id;



}
