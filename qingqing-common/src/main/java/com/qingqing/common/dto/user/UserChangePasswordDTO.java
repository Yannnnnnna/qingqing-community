package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@ApiModel(description = "修改密码信息")
public class UserChangePasswordDTO {

    /**
     * 用户ID（雪花算法生成的 ID）
     */
    @NotNull(message = "用户ID不能为空")
    @Positive(message = "用户ID必须为正数")
    @ApiModelProperty(value = "用户id", example = "1708892123456789012", required = true)
    private Long id;

    /**
     * 旧密码（必填，6-20位字母或数字）
     */
    @NotBlank(message = "旧密码不能为空")
    @Size(min = 6, max = 20, message = "旧密码长度必须为6-20位")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "旧密码只能包含字母或数字")
    @ApiModelProperty(value = "旧密码", example = "password123", required = true)
    private String oldPassword;

    /**
     * 新密码（必填，6-20位字母或数字，且不能与旧密码相同）
     */
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 20, message = "新密码长度必须为6-20位")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "新密码只能包含字母或数字")
    @ApiModelProperty(value = "新密码", example = "password123", required = true)
    private String newPassword;
}
