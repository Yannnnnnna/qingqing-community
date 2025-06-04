package com.qingqing.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改管理员信息
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("管理员更新数据传输模型")
public class AdminUpdateDTO {
    /**
     * 主键值
     */
    @ApiModelProperty(value = "主键值", example = "雪花生成id")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "admin")
    private String userName;
    /**
     * 角色
     */
    @ApiModelProperty(value = "角色", example = "超级管理员", notes = "管理员角色")
    private String role;
}
