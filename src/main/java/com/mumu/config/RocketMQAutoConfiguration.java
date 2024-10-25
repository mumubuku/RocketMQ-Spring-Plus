package com.mumu.config;


import com.mumu.manager.TopicManager;
import com.mumu.template.CustomRocketMQTemplate;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "rocketmq.enabled", havingValue = "true", matchIfMissing = true)
public class RocketMQAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RocketMQProperties rocketMQProperties() {
        return new RocketMQProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public TopicManager topicManager(RocketMQProperties properties) {
        return new TopicManager(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public CustomRocketMQTemplate customRocketMQTemplate(RocketMQTemplate rocketMQTemplate, TopicManager topicManager, MeterRegistry meterRegistry) {
        return new CustomRocketMQTemplate(rocketMQTemplate, topicManager, meterRegistry);
    }
}
