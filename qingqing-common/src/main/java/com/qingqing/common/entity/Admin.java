package com.qingqing.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Data
/*
 * callSuper = false
 * 不调用父类方法，仅比较当前类的字段
 * callSuper = true
 * 调用父类的 equals()/hashCode()，适合继承场景
 */
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Admin", description="管理员表")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键",  example = "雪花生成id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    @ApiModelProperty(value = "密码", example = "admin")
    private String password;

    @ApiModelProperty(value = "角色", example = "超级管理员")
    private String role;

    @ApiModelProperty(value = "状态(0-正常,1-禁用)",  example = "0")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间", example = "2025-05-28 19:28:17")
    private LocalDateTime createTime;

    // 更新时间：插入和更新时自动填充
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间", example = "2025-05-28 19:28:17")
    private LocalDateTime updateTime;


}
