package com.qingqing.common.dto;

/**
 * <p>
 * 描述：数据转换回调接口
 * </p>

 */
public interface DataTransCallBack<T, D> {
    /**
     * 执行转换
     * @param src 待转换对象
     * @return 转换目标对象
     */
    T executeTrans(D src);
}
