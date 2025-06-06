package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "修改密码信息")
public class UserChangePasswordDTO {
    @ApiModelProperty(value = "用户id", example = "1708892123456789012")
    private Long id;
    @ApiModelProperty(value = "旧密码", example = "password123")
    private String oldPassword;
    @ApiModelProperty(value = "新密码", example = "password123")
    private String newPassword;

}