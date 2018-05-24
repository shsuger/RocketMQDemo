package com.shsuger.simpleExample;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @Author shsuger
 * @Date 2018/3/13
 *
 * Reliable asynchronous transmission
 *
 * Application: asynchronous transmission is generally used in response time sensitive business scenarios.
 *
 */
public class AsyncProducer {
    public static void main(String[] args) throws Exception {

        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
        //Launch the instance.

        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        for (int i = 0; i < 100; i++) {
            final int index = i;

            //Create a message instance, specifying topic, tag and message body.

            Message msg = new Message("TopicTest", "TagA", "OrderID188", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));

            /**
             * Send message to broker asynchronously.
             * This method returns immediately. On sending completion, <code>sendCallback</code> will be executed.
             *
             */
            producer.send(msg, new SendCallback() {
                                                        @Override
                                                        public void onSuccess(SendResult sendResult) {
                                                            System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                                                        }
                                                        @Override
                                                        public void onException(Throwable e) {
                                                            System.out.printf("%-10d Exception %s %n", index, e);
                                                            e.printStackTrace();
                                                        }
                                                    }
                        );
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
