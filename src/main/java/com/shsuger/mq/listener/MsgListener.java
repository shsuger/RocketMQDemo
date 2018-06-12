package com.shsuger.mq.listener;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author shsuger
 * @Date 2018/5/31
 */
@Service
public class MsgListener implements MessageListenerConcurrently {

    private static final Logger logger = LoggerFactory.getLogger(MsgListener.class);

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        logger.info("获取到消息个数 size={}", msgs.size());
        if (CollectionUtils.isEmpty(msgs)) {
            logger.info("没有获取到消息信息");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }


        return null;
    }
}
