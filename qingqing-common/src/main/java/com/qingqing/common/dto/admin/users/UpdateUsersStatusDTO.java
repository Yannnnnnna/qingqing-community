package com.qingqing.common.dto.admin.users;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新用户状态
 *
 * @author wyr on 2025/6/3
 */
@ApiModel("更新用户状态模型")
@Data
public class UpdateUsersStatusDTO {
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "雪花id")
    private Long userId;

    /**
     * 状态
     * 0: 正常
     * 1: 禁用
     */
    @ApiModelProperty(value = "状态", example = "1", notes = "1: 禁用, 0：正常")
    private Integer status;
}
