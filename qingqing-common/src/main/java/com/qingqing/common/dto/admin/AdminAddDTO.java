package com.qingqing.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 添加管理员
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("添加管理员数据传输模型")
public class AdminAddDTO {

    /**
     * 用户名（必填，最大长度50，仅允许字母、数字、下划线）
     */
    @NotBlank(message = "用户名不能为空")
    @Size(max = 50, message = "用户名不能超过50个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    @ApiModelProperty(value = "用户名", example = "admin", required = true)
    private String username;

    /**
     * 密码（必填，最大长度100，仅允许字母或数字）
     */
    @NotBlank(message = "密码不能为空")
    @Size(max = 100, message = "密码不能超过100个字符")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "密码只能包含字母或数字")
    @ApiModelProperty(value = "密码", example = "admin", required = true, notes = "密码存储时应加密，前端不应直接展示密码")
    private String password;

    /**
     * 状态（可选，只能为0或1）
     */
    @Pattern(regexp = "^(0|1)?$", message = "状态必须为0或1")
    @ApiModelProperty(value = "状态", example = "0", notes = "0表示正常，1表示禁用")
    private String status;

    /**
     * 角色（可选，最大长度50）
     */
    @Size(max = 50, message = "角色名称不能超过50个字符")
    @ApiModelProperty(value = "角色", example = "超级管理员", notes = "管理员角色")
    private String role;
}
