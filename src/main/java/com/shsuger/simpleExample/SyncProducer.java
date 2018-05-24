package com.shsuger.simpleExample;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @Author shsuger
 * @Date 2018/3/13
 *
 * Reliable synchronous transmission
 *
 *
 * Application: Reliable synchronous transmission is used in extensive scenes,
 * such as important notification messages, SMS notification, SMS marketing system, etc..
 *
 *
 *
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {

        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        //Launch the instance.

        producer.setNamesrvAddr("localhost:9876");

        producer.start();

        for (int i = 0; i < 10; i++) {

            //Create a message instance, specifying topic, tag and message body.

            Message msg = new Message("ShsugerTopicTest" , "TagA" , ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) );

            /** Call send message to deliver message to one of brokers.
             *
             *  Send message in synchronous mode. This method returns only when the sending procedure totally completes.
             *
             *
             */
            SendResult sendResult = producer.send(msg);

            System.out.printf("%s%n", sendResult);

        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
