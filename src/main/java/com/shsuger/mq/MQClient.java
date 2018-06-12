package com.shsuger.mq;

import org.apache.rocketmq.client.producer.MQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author shsuger
 * @Date 2018/5/25
 */
public class MQClient {
    private static final Logger logger = LoggerFactory.getLogger(MQClient.class);

    final MQProducer producer;

    /**
     * 应用中只有一个生产者
     */
    public MQClient(final MQProducer producer) {
        if (producer == null) {
            throw new IllegalArgumentException("producer");
        }

        this.producer = producer;

    }



}
