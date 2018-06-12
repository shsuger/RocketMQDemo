package com.shsuger.orderExample;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * @Author shsuger
 * @Date 2018/3/13
 *
 * RocketMQ provides ordered messages using FIFO order.
 * The following example demonstrates sending/recieving of globally and partitionally ordered message.
 *
 *
 */
public class OrderedProducer {
    public static void main(String[] args) throws Exception {

        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("example_group_name");
        //Launch the instance.
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};

        /** Message queue selector, through which we get target message queue to deliver message to.
         *
         *  RocketMQ通过轮询所有队列的方式来确定消息被发送到哪一个队列（负载均衡策略）
         *  消息队列选择器，通过它我们获得目标消息队列来传递消息。也就是通过MessageQueueSelector中实现的算法来确定消息发送到哪一个队列上
         *  RocketMQ默认提供了两种 MessageQueueSelector 实现  ：随机/hash
         *
         *  当然，我们可以根据自己的业务场景来 决定 消息以何种策略发送到消息队列中
         *
         *
         *
         */
        MessageQueueSelector messageQueueSelector = new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                Integer id = (Integer) arg;   //arg 传入的是orderid
                int index = id % mqs.size();  //mqs.size默认为4
                return mqs.get(index);
            }
        };

        for (int i = 1000; i < 1100; i++) {
            int orderId = i % 10;  //
            //Create a message instance, specifying topic, tag and message body.


            Message msg = new Message("OrderedTopic", tags[i % tags.length], "KEY" + i, ("Hello RocketMQ HAHAHAHAH!!! !!!" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));


            SendResult sendResult = producer.send(msg, (mqs,msg1,arg)->{return mqs.get((Integer) arg % mqs.size());}, orderId);

            System.out.printf("%s%n", sendResult);
        }
        //server shutdown
        producer.shutdown();
    }
}
