package com.qingqing.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author wyr on 2025/6/4
 */
@Slf4j
@Component // 注册为Spring Bean
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时自动填充
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        // 确保DO中存在 createTime 字段
        // this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        // 或者使用更灵活的 setFieldValByName
        if (metaObject.hasSetter("createTime")) { // 检查是否有 createTime 字段的 setter
            this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }

        // 确保DO中存在 updateTime 字段
        // this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        if (metaObject.hasSetter("updateTime")) { // 检查是否有 updateTime 字段的 setter
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }

    /**
     * 更新时自动填充
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        // 确保DO中存在 updateTime 字段
        // this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        if (metaObject.hasSetter("updateTime")) { // 检查是否有 updateTime 字段的 setter
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}
