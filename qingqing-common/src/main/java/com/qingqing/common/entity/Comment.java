package com.qingqing.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Comment对象", description="评论表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "帖子ID")
    private Long postId;

    @ApiModelProperty(value = "评论者ID")
    private Long userId;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "父评论ID(0表示顶级评论)")
    private Long parentId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


}
