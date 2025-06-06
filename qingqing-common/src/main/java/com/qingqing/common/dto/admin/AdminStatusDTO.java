package com.qingqing.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 启用禁用管理员
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("管理员状态数据传输模型")
public class AdminStatusDTO {

    /**
     * 管理员ID（雪花算法生成的 ID）
     */
    @NotNull(message = "管理员ID不能为空")
    @Positive(message = "管理员ID必须为正数")
    @ApiModelProperty(value = "管理员ID", example = "1930100064880316417", required = true)
    private Long id;

    /**
     * 状态（只能是 0 或 1）
     */
    @Min(value = 0, message = "状态必须为0或1")
    @Max(value = 1, message = "状态必须为0或1")
    @ApiModelProperty(value = "状态", example = "0", notes = "0表示启用，1表示禁用", required = true)
    private int status;
}
