package com.qingqing.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * JSON工具类
 * 用于处理JSON字符串与对象之间的转换
 *
 * @author anonymous
 * @since 2025-06-06
 */
public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将JSON字符串转换为字符串列表
     * 主要用于解析商品图片URL列表
     *
     * @param jsonString JSON字符串
     * @return 字符串列表，解析失败时返回空列表
     */
    public static List<String> parseJsonToStringList(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            logger.debug("JSON字符串为空或null");
            return Collections.emptyList();
        }

        try {
            List<String> result = objectMapper.readValue(jsonString, new TypeReference<List<String>>() {});
            logger.debug("成功解析JSON字符串: {}", jsonString);
            return result != null ? result : Collections.emptyList();
        } catch (Exception e) {
            logger.error("解析JSON字符串失败: {}, 错误信息: {}", jsonString, e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * 将字符串列表转换为JSON字符串
     * 主要用于保存商品图片URL列表
     *
     * @param stringList 字符串列表
     * @return JSON字符串，转换失败时返回空数组JSON字符串
     */
    public static String convertStringListToJson(List<String> stringList) {
        if (stringList == null) {
            logger.debug("字符串列表为null，返回空数组JSON");
            return "[]";
        }

        try {
            String result = objectMapper.writeValueAsString(stringList);
            logger.debug("成功转换字符串列表为JSON: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("转换字符串列表为JSON失败: {}, 错误信息: {}", stringList, e.getMessage());
            return "[]";
        }
    }

    /**
     * 获取图片列表的第一张图片URL
     * 如果列表为空或null，返回null
     *
     * @param imageList 图片URL列表
     * @return 第一张图片的URL，如果没有图片则返回null
     */
    public static String getFirstImage(List<String> imageList) {
        if (imageList != null && !imageList.isEmpty()) {
            return imageList.get(0);
        }
        return null;
    }

    /**
     * 从JSON字符串中直接获取第一张图片URL
     * 这是一个便捷方法，组合了JSON解析和获取第一张图片的功能
     *
     * @param imagesJson 图片JSON字符串
     * @return 第一张图片的URL，如果没有图片则返回null
     */
    public static String getFirstImageFromJson(String imagesJson) {
        List<String> imageList = parseJsonToStringList(imagesJson);
        return getFirstImage(imageList);
    }
}