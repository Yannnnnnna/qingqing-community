package com.qingqing.common.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理员登录VO
 *
 * @author wyr on 2025/6/2
 */
@Data
@ApiModel ("管理员登录VO")
public class AdminLoginVO {

    @ApiModelProperty(name = "主键值", example = "雪花生成id")
    private Long id;

    @ApiModelProperty(name = "用户名", example = "admin")
    private String userName;

    @ApiModelProperty(name = "角色", example = "超级管理员")
    private String role;
    @ApiModelProperty(name = "状态", example = "1", notes = "0:启用, 1:禁用")
    private Integer status;
    @ApiModelProperty(name = "创建时间", example = "2025-06-02 10:10:10")
    private LocalDateTime createTime;
    @ApiModelProperty(name = "更新时间", example = "2025-06-02 10:10:10")
    private LocalDateTime updateTime;

    @ApiModelProperty(name = "jwt令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String token;

}
