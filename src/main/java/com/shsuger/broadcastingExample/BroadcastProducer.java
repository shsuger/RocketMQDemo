package com.shsuger.broadcastingExample;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @Author shsuger
 * @Date 2018/3/16
 *
 * Broadcasting is sending a message to all subscribers of a topic.
 * If you want all subscribers receive messages about a topic, broadcasting is a good choice.
 *
 *
 */
public class BroadcastProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");

        producer.setNamesrvAddr("localhost:9876");

        producer.start();

        for (int i = 0; i < 100; i++){
            Message msg = new Message("TopicTest", "TagA", "OrderID188", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));

            //Send message in synchronous mode. This method returns only when the sending procedure totally completes.

            SendResult sendResult = producer.send(msg);

            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}
