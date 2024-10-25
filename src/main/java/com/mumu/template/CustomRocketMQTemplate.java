package com.mumu.template;



import com.alibaba.fastjson.JSONObject;
import com.mumu.constant.RocketMQSysConstant;
import com.mumu.domain.BaseMQMessage;
import com.mumu.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;



/**
 * RocketMQ模板类
 *
 * @author tianxincoord@163.com
 * @since 2022/4/15
 */
@Slf4j
@Component
public class CustomRocketMQTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomRocketMQTemplate.class);

    @Autowired
    private RocketMQTemplate template;

    /**
     * 获取模板，如果封装的方法不够提供原生的使用方式
     */
    public RocketMQTemplate getTemplate() {
        return template;
    }

    /**
     * 构建目的地
     */
    public String buildDestination(String topic, String tag) {
        return topic + RocketMQSysConstant.DELIMITER + tag;
    }

    /**
     * 发送同步消息
     */
    public <T extends BaseMQMessage> SendResult send(String topic, String tag, T message) {
        // 注意分隔符
        return send(topic + RocketMQSysConstant.DELIMITER + tag, message);
    }

    public <T extends BaseMQMessage> SendResult send(String destination, T message) {
        // 设置业务键，此处根据公共的参数进行处理
        // 更多的其它基础业务处理...
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
        SendResult sendResult = template.syncSend(destination, sendMessage);
        // 此处为了方便查看给日志转了json，根据选择选择日志记录方式，例如ELK采集
        LOGGER.info("[{}]同步消息[{}]发送结果[{}]", destination, JsonUtil.toJson(message), JSONObject.toJSON(sendResult));
        return sendResult;
    }

    /**
     * 发送延迟消息
     */
    public <T extends BaseMQMessage> SendResult send(String topic, String tag, T message, int delayLevel) {
        return send(topic + RocketMQSysConstant.DELIMITER + tag, message, delayLevel);
    }

    public <T extends BaseMQMessage> SendResult send(String destination, T message, int delayLevel) {
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
        SendResult sendResult = template.syncSend(destination, sendMessage, 3000, delayLevel);
        LOGGER.info("[{}]延迟等级[{}]消息[{}]发送结果[{}]", destination, delayLevel, JsonUtil.toJson(message), JsonUtil.toJson(sendResult));
        return sendResult;
    }


    /**
     * 通用的MQ消息发送方法
     * @param topic MQ-topic
     * @param tag MQ-tag
     * @param params 可变参数，用于拼接消息
     * @return Boolean 消息是否发送成功
     */
    public Boolean sendMessage(String topic, String tag, Object... params) {
        // 如果 tag 或 topic 为空则直接返回失败
        if (ObjectUtils.isEmpty(tag) || ObjectUtils.isEmpty(topic)) {
            log.warn("发送消息失败：topic 或 tag 为空");
            return false;
        }

        // 构建消息字符串
        String message = String.join(",", convertToStringArray(params));

        // 发送消息
        try {
            template.convertAndSend(String.format("%s:%s", topic, tag), message);
            log.info("消息发送成功，Topic: {}, Tag: {}, Message: {}", topic, tag, message);
            return true;
        } catch (Exception e) {
            log.error("消息发送异常: ", e);
            return false;
        }
    }

    /**
     * 将可变参数转换为字符串数组
     * @param params 可变参数
     * @return 字符串数组
     */
    private String[] convertToStringArray(Object... params) {
        String[] strArray = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            strArray[i] = String.valueOf(params[i]);
        }
        return strArray;
    }

}
