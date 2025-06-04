package com.qingqing.common.dto.admin.goods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品详细信息展示
 *
 * @author wyr on 2025/6/3
 */
@Slf4j
@Data
@ApiModel("商品详细信息展示")
public class GoodsDTO {
    @ApiModelProperty(value = "商品ID", example = "雪花生成的id")
    private Long id; // 商品ID

    @ApiModelProperty(value = "发布者名字", example = "张三")
    private String publisherName; // 发布者名字

    @ApiModelProperty(value = "类别名", example = "书籍")
    private String categoryName; // 类别名

    @ApiModelProperty(value = "商品标题", example = "Java编程思想")
    private String title; // 商品标题

    @ApiModelProperty(value = "商品价格", example = "59.9")
    private Double price; // 商品价格

    @ApiModelProperty(value = "商品状态 (0: 未审核, 1: 审核通过, 2: 已下架, 3: 已售出)", example = "1")
    private Integer status; // 商品状态 (0: 未审核, 1: 审核通过, 2: 已下架, 3: 已售出)

    @ApiModelProperty(value = "创建时间", example = "2025-06-03 10:00:00")
    private LocalDateTime createTime; // 创建时间

    // 数据库存储字段（JSON字符串）
    @JsonIgnore // 不在API文档中显示
    private String images; // 存储JSON字符串

    // API传输字段（URL列表）
    @ApiModelProperty(value = "商品图片URL列表", example = "[\"https://example.com/goods1.jpg\", \"https://example.com/goods2.jpg\"]")
    private List<String> imageUrls; // 图片URL列表

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将 JSON 字符串解析为图片 URL 列表
     * 从数据库读取后调用此方法
     */
    public void parseImagesFromJson() {
        if (images != null && !images.trim().isEmpty()) {
            try {
                this.imageUrls = objectMapper.readValue(images, new TypeReference<List<String>>() {});
            } catch (JsonProcessingException e) {
                log.error("解析图片JSON字符串失败: {}", images, e);
                this.imageUrls = new ArrayList<>();
            }
        } else {
            this.imageUrls = new ArrayList<>();
        }
    }

    /**
     * 将图片 URL 列表转换为 JSON 字符串
     * 保存到数据库前调用此方法
     */
    public void convertImagesToJson() {
        if (imageUrls != null && !imageUrls.isEmpty()) {
            try {
                this.images = objectMapper.writeValueAsString(imageUrls);
            } catch (JsonProcessingException e) {
                log.error("转换图片URL列表为JSON失败: {}", imageUrls, e);
                this.images = "[]";
            }
        } else {
            this.images = "[]";
        }
    }

    /**
     * 设置图片URL列表（同时更新JSON字符串）
     */
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        convertImagesToJson();
    }

    /**
     * 添加单个图片URL
     */
    public void addImageUrl(String imageUrl) {
        if (this.imageUrls == null) {
            this.imageUrls = new ArrayList<>();
        }
        this.imageUrls.add(imageUrl);
        convertImagesToJson();
    }
}
