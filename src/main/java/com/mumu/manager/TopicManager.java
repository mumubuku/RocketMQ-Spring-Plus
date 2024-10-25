package com.mumu.manager;


import com.mumu.config.RocketMQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class TopicManager {

    private final Map<String, RocketMQProperties.TopicConfig> topicMap = new HashMap<>();

    @Autowired
    public TopicManager(RocketMQProperties properties) {
        for (RocketMQProperties.TopicConfig topicConfig : properties.getTopics()) {
            topicMap.put(topicConfig.getName(), topicConfig);
        }
    }

    /**
     * 根据 topic 名称获取 topic 对象
     * @param topicName topic 名称
     * @return Optional<RocketMQProperties.TopicConfig>
     */
    public Optional<RocketMQProperties.TopicConfig> getTopicConfig(String topicName) {
        return Optional.ofNullable(topicMap.get(topicName));
    }

    /**
     * 根据 topic 名称和 tag 获取完整的 topic 标签字符串
     * @param topicName topic 名称
     * @param tag tag 名称
     * @return 完整的 topic:tag 字符串
     */
    public String getTopicWithTag(String topicName, String tag) {
        return getTopicConfig(topicName)
                .map(topicConfig -> {
                    if (topicConfig.getTags().containsKey(tag)) {
                        return topicConfig.getName() + ":" + tag;
                    } else {
                        throw new IllegalArgumentException("Tag not found: " + tag);
                    }
                })
                .orElseThrow(() -> new IllegalArgumentException("Topic not found: " + topicName));
    }

    /**
     * 获取某个 topic 的所有 tag
     * @param topicName topic 名称
     * @return tag 名称的集合
     */
    public Map<String, String> getTagsForTopic(String topicName) {
        return getTopicConfig(topicName)
                .map(RocketMQProperties.TopicConfig::getTags)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found: " + topicName));
    }
}
