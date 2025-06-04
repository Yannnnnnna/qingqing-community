package com.qingqing.common.dto.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分页查询结果
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("管理员数据传输模型")
public class AdminDTO {
    /**
     * 主键值
     */
    @ApiModelProperty(value = "主键值", example = "雪花生成id")
    private Long id;

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
    private Integer status;

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色", example = "超级管理员", notes = "管理员角色")
    private String role;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2025-06-03 12:00:00", notes = "格式为yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
