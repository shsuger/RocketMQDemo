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

        for (int i = 0; i < 10; i++) {
            int orderId = i % 10;  //
            //Create a message instance, specifying topic, tag and message body.


            Message msg = new Message("OrderedTopic", tags[i % tags.length], "KEY" + i, ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            /** Message queue selector, through which we get target message queue to deliver message to.
             *  消息队列选择器，通过它我们获得目标消息队列来传递消息。
             *
             */
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                                                                                        @Override
                                                                                        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {


                                                                                            Integer id = (Integer) arg;   //arg 传入的是orderid
                                                                                            int index = id % mqs.size();  //mqs.size默认为4
                                                                                            return mqs.get(index);
                                                                                        }
                                                                                    },
                                                    orderId

                                                );

            System.out.printf("%s%n", sendResult);
        }
        //server shutdown
        producer.shutdown();
    }
}
