package com.shsuger.simpleExample;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @Author shsuger
 * @Date 2018/3/13
 *
 * One-way transmission
 * Application: One-way transmission is used for cases requiring moderate reliability, such as log collection.
 *
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception{

        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        //Launch the instance.

        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        for (int i = 0; i < 100; i++) {

            //Create a message instance, specifying topic, tag and message body.

            Message msg = new Message("TopicTest" , "TagA" , ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            /** Call send message to deliver message to one of brokers.
             *
             *  this method won't wait for acknowledgement from broker before return.
             *  Obviously, it has maximums throughput yet potentials of message loss.
             *  只管发
             *
             */
            producer.sendOneway(msg);

        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
