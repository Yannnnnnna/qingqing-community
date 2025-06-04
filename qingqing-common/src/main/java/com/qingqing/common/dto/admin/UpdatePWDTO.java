package com.qingqing.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改密码dto
 *
 * @author wyr on 2025/6/2
 */
@Data
@ApiModel(description = "修改密码DTO")
public class UpdatePWDTO {
    @ApiModelProperty(value = "主键值", example = "雪花生成id")
    private Long id;
    @ApiModelProperty(value = "旧密码", example = "admin")
    private String oldPassword; // 旧密码
    @ApiModelProperty(value = "新密码", example = "admin123")
    private String newPassword; // 新密码

}
