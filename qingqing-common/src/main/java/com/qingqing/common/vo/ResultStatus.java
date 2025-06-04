package com.qingqing.common.vo;

/**
 * <p>
 * 描述：响应码枚举
 * </p>
 */
public enum ResultStatus {
    // ========== 成功响应 ==========
    SUCCESS("操作成功", 200),

    // ========== 客户端错误 4xx ==========
    BAD_REQUEST("请求参数错误", 400),
    UNAUTHORIZED("暂未登录或TOKEN已经过期", 401),
    FORBIDDEN("没有相关权限", 403),
    NOT_FOUND("请求的资源不存在", 404),
    METHOD_NOT_ALLOWED("请求方法不被允许", 405),
    CONFLICT("数据冲突", 409),
    TOO_MANY_REQUESTS("请求过于频繁", 429),

    // ========== 服务器错误 5xx ==========
    INTERNAL_SERVER_ERROR("服务器内部错误", 500),
    BAD_GATEWAY("网关错误", 502),
    SERVICE_UNAVAILABLE("服务暂不可用", 503),
    GATEWAY_TIMEOUT("网关超时", 504),

    // ========== 业务错误码 1xxx ==========
    // 用户相关 10xx
    USER_NOT_EXIST("用户不存在", 1001),
    USER_DISABLED("用户已被禁用", 1002),
    USER_ALREADY_EXIST("用户已存在", 1003),
    PASSWORD_ERROR("密码错误", 1004),
    OLD_PASSWORD_ERROR("原密码错误", 1005),
    USER_INFO_INCOMPLETE("用户信息不完整", 1006),

    // 认证授权相关 11xx
    TOKEN_INVALID("Token无效", 1101),
    TOKEN_EXPIRED("Token已过期", 1102),
    REFRESH_TOKEN_INVALID("刷新Token无效", 1103),
    LOGIN_FAILED("登录失败", 1104),
    LOGOUT_FAILED("登出失败", 1105),
    ACCOUNT_LOCKED("账户已被锁定", 1106),

    // 参数验证相关 12xx
    PARAMS_INVALID("请求参数无效", 1201),
    PARAMS_MISSING("缺少必要参数", 1202),
    PARAMS_TYPE_ERROR("参数类型错误", 1203),
    PARAMS_FORMAT_ERROR("参数格式错误", 1204),
    FILE_UPLOAD_ERROR("文件上传失败", 1205),
    FILE_TYPE_NOT_SUPPORTED("不支持的文件类型", 1206),
    FILE_SIZE_EXCEEDED("文件大小超出限制", 1207),

    // 业务逻辑相关 13xx
    OPERATION_FAILED("操作失败", 1301),
    DATA_NOT_EXIST("数据不存在", 1302),
    DATA_ALREADY_EXIST("数据已存在", 1303),
    DATA_IN_USE("数据正在使用中，无法删除", 1304),
    OPERATION_NOT_ALLOWED("当前状态不允许此操作", 1305),
    INSUFFICIENT_PERMISSIONS("权限不足", 1306),
    QUOTA_EXCEEDED("配额已超出", 1307),

    // 外部服务相关 14xx
    THIRD_PARTY_SERVICE_ERROR("第三方服务异常", 1401),
    SMS_SEND_FAILED("短信发送失败", 1402),
    EMAIL_SEND_FAILED("邮件发送失败", 1403),
    PAYMENT_FAILED("支付失败", 1404),

    // 系统相关 15xx
    DATABASE_ERROR("数据库操作异常", 1501),
    REDIS_ERROR("缓存服务异常", 1502),
    MQ_ERROR("消息队列异常", 1503),
    NETWORK_ERROR("网络异常", 1504),
    TIMEOUT_ERROR("请求超时", 1505),

    // 内容相关 16xx
    CONTENT_TYPE_ERROR("Content-Type错误", 1601),
    CONTENT_TOO_LONG("内容过长", 1602),
    CONTENT_ILLEGAL("内容违规", 1603),

    // 系统维护相关 17xx
    SYSTEM_MAINTENANCE("系统维护中", 1701),
    FEATURE_NOT_IMPLEMENTED("功能尚未实现", 1702),
    API_VERSION_NOT_SUPPORTED("API版本不支持", 1703);

    private final String message;
    private final int code;

    ResultStatus(String message, int code) {
        this.message = message;
        this.code = code;
    }

    ResultStatus(String message) {
        this(message, 200);
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    /**
     * 根据状态码获取对应的枚举
     * @param code 状态码
     * @return ResultStatus枚举，如果找不到则返回null
     */
    public static ResultStatus getByCode(int code) {
        for (ResultStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }

    /**
     * 判断是否为成功状态
     * @return true表示成功，false表示失败
     */
    public boolean isSuccess() {
        return this.code == 200;
    }
}