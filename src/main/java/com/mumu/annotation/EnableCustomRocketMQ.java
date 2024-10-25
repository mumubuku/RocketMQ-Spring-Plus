package com.mumu.annotation;




import com.mumu.config.RocketMQAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于启用 RocketMQ 扩展配置
 * @author mumu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RocketMQAutoConfiguration.class)
public @interface EnableCustomRocketMQ {
}
