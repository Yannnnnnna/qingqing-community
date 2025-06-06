package com.qingqing.common.dto.admin.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 商品分类更新模型
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("商品分类更新模型")
public class CategoryUpdateDTO extends CategoryAddDTO {

    /**
     * 分类ID（雪花算法生成的 ID）
     */
    @NotNull(message = "分类ID不能为空")
    @Positive(message = "分类ID必须为正数")
    @ApiModelProperty(value = "分类ID", example = "1930100064880316417", required = true)
    private Long id;

}
