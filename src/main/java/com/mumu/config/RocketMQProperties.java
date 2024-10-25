package com.mumu.config;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author mumu
 */
@Getter
@Configuration
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMQProperties {
    // getters and setters for other fields
    private String namesrvAddr;
    private String producerGroup;
    private String consumerGroup;
    private int retryTimes;
    private int sendTimeout;

    private List<TopicConfig> topics;

    public static class TopicConfig {
        private String name;
        private Map<String, String> tags;

        // getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, String> getTags() {
            return tags;
        }

        public void setTags(Map<String, String> tags) {
            this.tags = tags;
        }
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public void setSendTimeout(int sendTimeout) {
        this.sendTimeout = sendTimeout;
    }

    public void setTopics(List<TopicConfig> topics) {
        this.topics = topics;
    }
}
