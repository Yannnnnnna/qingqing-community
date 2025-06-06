package com.qingqing.common.dto.admin.users;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 更新用户状态
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("更新用户状态模型")
public class UpdateUsersStatusDTO {

    /**
     * 用户ID（雪花算法生成的 ID）
     */
    @NotNull(message = "用户ID不能为空")
    @Positive(message = "用户ID必须为正数")
    @ApiModelProperty(value = "用户ID", example = "1930100064880316417", required = true)
    private Long userId;

    /**
     * 状态（只能是0或1）
     */
    @NotNull(message = "状态不能为空")
    @Min(value = 0, message = "状态必须为0或1")
    @Max(value = 1, message = "状态必须为0或1")
    @ApiModelProperty(value = "状态", example = "1", notes = "1: 禁用, 0：正常", required = true)
    private Integer status;
}
