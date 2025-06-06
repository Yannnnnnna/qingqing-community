package com.qingqing.common.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime; // 推荐使用 LocalDateTime 处理日期时间

/**
 * <p>
 * 商品消息表 实体类
 * </p>
 *
 * @author anonymous // 请替换为您的作者名
 * @since 2025-06-06 // 根据当前日期调整
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true) // 启用链式设置方法
@TableName("message") // 映射到数据库的 message 表
@ApiModel(value="Message对象", description="商品消息表")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息ID，主键 (由雪花算法生成)")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "所属商品ID")
    @TableField("goods_id")
    private Long goodsId;

    @ApiModelProperty(value = "发送方用户ID")
    @TableField("sender_id")
    private Long senderId;

    @ApiModelProperty(value = "接收方用户ID")
    @TableField("receiver_id")
    private Long receiverId;

    @ApiModelProperty(value = "消息内容类型 (0-文本, 1-图片URL)")
    @TableField("content_type")
    private Integer contentType; // TINYINT 通常映射为 Java 的 Integer 或 Byte

    @ApiModelProperty(value = "消息内容 (文本内容或图片URL)")
    @TableField("content")
    private String content;


    @ApiModelProperty(value = "消息发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 格式化日期时间用于JSON序列化
    @TableField("create_time")
    private LocalDateTime createTime; // DATETIME 推荐映射为 LocalDateTime
}