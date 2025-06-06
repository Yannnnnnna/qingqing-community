package com.qingqing.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 修改管理员信息
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("管理员更新数据传输模型")
public class AdminUpdateDTO {

    /**
     * 主键值（雪花算法生成的 ID）
     */
    @NotNull(message = "主键ID不能为空")
    @Positive(message = "主键ID必须为正数")
    @ApiModelProperty(value = "主键值", example = "1930100064880316417", required = true)
    private Long id;

    /**
     * 用户名（可选，若传入则需符合格式）
     */
    @Size(max = 50, message = "用户名不能超过50个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    @ApiModelProperty(value = "用户名", example = "admin")
    private String userName;

    /**
     * 角色（可选，最大长度50）
     */
    @Size(max = 50, message = "角色名称不能超过50个字符")
    @ApiModelProperty(value = "角色", example = "超级管理员", notes = "管理员角色")
    private String role;
}
