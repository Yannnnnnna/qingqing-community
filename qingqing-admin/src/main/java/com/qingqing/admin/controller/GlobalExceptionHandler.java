package com.qingqing.admin.controller;

import com.qingqing.common.constants.MessageConstant;
import com.qingqing.common.exception.BaseException;
import com.qingqing.common.vo.JsonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError; // 导入 FieldError
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice // 结合 @ControllerAdvice 和 @ResponseBody
@Slf4j // 使用 Lombok 提供日志功能
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常 (BaseException)
     * 当业务逻辑中主动抛出继承自 BaseException 的异常时，会由此方法捕获并处理。
     *
     * @param ex 业务异常对象
     * @return 包含错误信息的统一 JSON 响应
     */
    @ExceptionHandler(BaseException.class) // 明确指定要处理的异常类型
    public JsonVO<String> handleBaseException(BaseException ex) {
        log.error("业务异常信息：{}", ex.getMessage()); // 记录详细的异常信息
        return JsonVO.fail(ex.getMessage()); // 返回业务异常中定义的错误消息
    }

    /**
     * 处理 SQL 完整性约束冲突异常 (如唯一性约束冲突)
     * 当数据库操作违反了完整性约束（例如插入重复数据）时，会由此方法捕获。
     *
     * @param e SQL 完整性约束冲突异常对象
     * @return 包含错误信息的统一 JSON 响应
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class) // 明确指定要处理的异常类型
    public JsonVO<String> handleSqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        String message = e.getMessage();
        log.error("SQL 异常信息：{}", message); // 记录详细的 SQL 异常信息

        if (message != null && message.contains("Duplicate entry")) {
            // 解析重复条目信息，例如 "Duplicate entry 'username' for key '...' "
            String[] split = message.split(" ");
            if (split.length >= 3) {
                String duplicateValue = split[2].replace("'", ""); // 提取重复的值，去除引号
                String msg = duplicateValue + MessageConstant.ALREADY_EXISTS; // 拼接友好的错误信息
                return JsonVO.fail(msg);
            }
        }
        // 对于其他类型的 SQL 完整性约束冲突或解析失败的情况，返回通用错误
        return JsonVO.fail(MessageConstant.UNKNOWN_ERROR);
    }

    /**
     * 处理 @RequestBody 参数校验失败的异常 (MethodArgumentNotValidException)
     * 当使用 @Valid 或 @Validated 注解对请求体参数进行校验失败时，会由此方法捕获。
     *
     * @param e 参数校验失败异常对象
     * @return 包含错误信息的统一 JSON 响应 (通常不返回具体数据，Void 类型)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonVO<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        // 确保 FieldError 被正确导入
        List<String> errorMessages = bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage) // 获取每个字段的默认错误消息
                .collect(Collectors.toList());

        // 如果有多个错误信息，将它们拼接起来
        String errorMessage = "参数校验失败: " + String.join(", ", errorMessages);
        log.warn("参数校验失败：{}", errorMessage); // 记录警告信息，因为这通常是客户端输入问题

        // 使用您定义的 JsonVO 返回失败信息和错误码
        // 通常参数校验失败返回 400 Bad Request
        // 这里假设您的 JsonVO.fail(Object data, Integer code, String message) 方法
        return JsonVO.fail(errorMessage);
    }
    /**
     * 处理 @Validated 注解在普通参数（非 @RequestBody）上的校验失败异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public JsonVO<Void> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<String> errorMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        String errorMessage = "参数校验失败: " + String.join(", ", errorMessages);
        log.warn("URL 参数校验失败：{}", errorMessage);

        // 返回统一的 JsonVO 响应
        return JsonVO.fail(errorMessage); // 同样返回 400 Bad Request
    }
    /**
     * 捕获所有其他未被特定处理的异常
     * 这是一个“万能”的异常处理器，用于捕获所有其他未明确处理的 RuntimeException。
     *
     * @param ex 运行时异常对象
     * @return 包含通用错误信息的统一 JSON 响应
     */
    @ExceptionHandler(Exception.class) // 捕获所有类型的异常
    public JsonVO<String> handleGenericException(Exception ex) {
        log.error("发生未知异常：", ex); // 记录完整的堆栈信息，便于排查
        return JsonVO.fail(MessageConstant.UNKNOWN_ERROR); // 返回通用错误消息
    }
}