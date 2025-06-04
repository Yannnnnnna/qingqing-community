package com.qingqing.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 启用禁用管理员
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("管理员状态数据传输模型")
public class AdminStatusDTO {
    @ApiModelProperty(value = "管理员ID", example = "雪花")
    private Long id; // 管理员ID
    @ApiModelProperty(value = "状态", example = "0", notes = "0表示启用，1表示禁用")
    private int status;
}
