package com.qingqing.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 添加管理员
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("添加管理员数据传输模型")
public class AdminAddDTO {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", example = "******", notes = "密码存储时应加密，前端不应直接展示密码")
    private String password;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", example = "0", notes = "0表示正常，1表示禁用")
    private String status;

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色", example = "超级管理员", notes = "管理员角色")
    private String role;


}
