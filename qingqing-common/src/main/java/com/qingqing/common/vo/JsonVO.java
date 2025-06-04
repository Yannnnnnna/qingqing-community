package com.qingqing.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 后端统一返回结果
 * @param <T>
 */
@Data
public class JsonVO<T> implements Serializable {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码", example = "200")
    private Integer code;

    /**
     * 提示消息
     */
    @ApiModelProperty(value = "提示消息", example = "操作成功")
    private String message;

    /**
     * 数据对象
     */
    @ApiModelProperty(value = "数据对象")
    private T data;

    /**
     * 通过结果枚举设置状态码和提示消息
     * @param resultStatus 结果状态枚举
     */
    private void setStatus(ResultStatus resultStatus) {
        this.setMessage(resultStatus.getMessage());
        this.setCode(resultStatus.getCode());
    }

    /**
     * 静态构建方式
     * @param data 获取的数据
     * @param resultStatus 提示信息
     * @param <T> JsonVO嵌套元素类型
     * @return 返回创建的JSON VO对象
     */
    public static <T> JsonVO<T> create(T data, ResultStatus resultStatus) {
        JsonVO<T> result = new JsonVO<>();
        result.setStatus(resultStatus);
        result.setData(data);
        return result;
    }

    /**
     * 静态构建方式
     * @param data 获取的数据
     * @param code 状态码
     * @param message 提示信息
     * @param <T> JsonVO嵌套元素类型
     * @return 返回创建的JSON VO对象
     */
    public static <T> JsonVO<T> create(T data, int code, String message) {
        JsonVO<T> result = new JsonVO<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 成功响应（带数据）
     */
    public static <T> JsonVO<T> success(T data) {
        return create(data, ResultStatus.SUCCESS);
    }

    /**
     * 成功响应（无数据）
     */
    public static <T> JsonVO<T> success() {
        return create(null, ResultStatus.SUCCESS);
    }

    /**
     * 成功响应（自定义消息）
     */
    public static <T> JsonVO<T> success(T data, String message) {
        return create(data, 200, message);
    }

    /**
     * 失败响应（使用默认失败状态）
     */
    public static <T> JsonVO<T> fail() {
        return create(null, ResultStatus.OPERATION_FAILED);
    }

    /**
     * 失败响应（带数据）
     */
    public static <T> JsonVO<T> fail(T data) {
        return create(data, ResultStatus.OPERATION_FAILED);
    }

    /**
     * 失败响应（指定状态码）
     */
    public static <T> JsonVO<T> fail(ResultStatus resultStatus) {
        return create(null, resultStatus);
    }

    /**
     * 失败响应（自定义消息）
     */
    public static <T> JsonVO<T> fail(String message) {
        return create(null, 500, message);
    }

    /**
     * 失败响应（带数据和指定状态）
     */
    public static <T> JsonVO<T> fail(T data, ResultStatus resultStatus) {
        return create(data, resultStatus);
    }
}