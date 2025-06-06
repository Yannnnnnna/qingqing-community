package com.qingqing.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 修改密码dto
 *
 * @author wyr on 2025/6/2
 */
@Data
@ApiModel(description = "修改密码DTO")
public class UpdatePWDTO implements Serializable {

    /**
     * 主键值，雪花算法生成，必须为大于0的数字
     */
    @NotNull(message = "主键ID不能为空")
    @Positive(message = "主键ID必须为正数")
    @ApiModelProperty(value = "主键值", example = "1930100064880316417", required = true)
    private Long id;

    /**
     * 旧密码，非空，仅包含字母和数字
     */
    @NotBlank(message = "旧密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "密码只能包含字母或数字")
    @ApiModelProperty(value = "旧密码", example = "admin", required = true)
    private String oldPassword;

    /**
     * 新密码，非空，仅包含字母和数字
     */
    @NotBlank(message = "新密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "密码只能包含字母或数字")
    @ApiModelProperty(value = "新密码", example = "admin123", required = true)
    private String newPassword;
}
