package com.shsuger.producer;

import com.meizu.mqclient.msg.order.FeeSplitMsg;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author shsuger
 * @Date 2018/3/15
 */
public class FeeSplitMsgProducer {
    private static final Logger logger = LoggerFactory.getLogger(FeeSplitMsgProducer.class);

    public static void main(String[] args) throws Exception {


        DefaultMQProducer producer = new
                DefaultMQProducer("please_rename_unique_group_name");
        //Launch the instance.

        producer.setNamesrvAddr("localhost:9876");
//        producer.setNamesrvAddr("172.17.60.91:9876");

        producer.start();

        String parentOrderSn = "200008";
        String orderSn = "orderSn-2006000";
        String bussType = "02";
        Integer tradeType = 1;

        double refoundAmount = 200;

        for (int i = 102; i < 103; i++) {

            String bussNo = "ordersn-20060012_2" + i;

            orderSn = "orderSn1-2006100" + i;

            FeeSplitMsg feeSplitMsg = buildFeeSplitMsg(parentOrderSn, orderSn, bussType, tradeType, bussNo, refoundAmount);


            String  msg1 = feeSplitMsg.toJsonMsg();

            try {

                SendStatus status = send(feeSplitMsg,producer);

                System.out.println(status);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static SendStatus send( FeeSplitMsg feeSplitMsg,DefaultMQProducer producer) throws Exception {




        final Message msg = new Message(feeSplitMsg.getTopic(),
                feeSplitMsg.getTags(),// tag
                feeSplitMsg.getKeys(),// key
                (feeSplitMsg.toJsonMsg()).getBytes("utf-8"));// body

        SendResult sendResult = producer.send(msg);

        logger.info("消息发送结果:{},topic:{},tags:{},keys:{},message body:{}", sendResult.toString(), feeSplitMsg.getTopic(), feeSplitMsg.getTags(), feeSplitMsg.getKeys(), feeSplitMsg.toJsonMsg());
        return sendResult.getSendStatus();

    }

    private static FeeSplitMsg buildFeeSplitMsg(String parentOrderSn, String orderSn, String bussType, Integer tradeType,
                                                String bussNo, double refoundAmount) {

        FeeSplitMsg msg = new FeeSplitMsg(orderSn);
        Long currentTime = System.currentTimeMillis();
        if (tradeType.equals(1)) {
            msg.setNeedPayAmount(66.34);
            msg.setParentNeedPay(66.34);
            msg.setParentRealPay(0.83);
            msg.setRealPayAmount(0.83);
        } else {
            msg.setNeedPayAmount(refoundAmount);
            msg.setParentNeedPay(refoundAmount);
            msg.setRealPayAmount(refoundAmount);
            msg.setParentRealPay(refoundAmount);
        }
        msg.setBussSn(bussNo);
        msg.setBussType(bussType);
        msg.setOrderSN(orderSn);
        msg.setTradeType(tradeType);
        msg.setParentOrderSn(parentOrderSn);
        msg.setBuyerUserId("00000001");
        msg.setBuyerUserName("buyer_user_test");
        msg.setMerchantId(1);
        msg.setCouponAmount(0);
        msg.setIntegralDedAmount(0);
        msg.setLogisticsAmount(0);

        msg.setOrderCompletedTime(currentTime);
        msg.setOrderCreateTime(currentTime);
        msg.setPayTradeNo("paycallno-0015000");
        msg.setPayType(0);// 0-支付宝
        // 场景两个字订单 skuid 1 2
        msg.setProductDesc("PRO-黑色");
        msg.setProductCount(1);
        msg.setProductCategoryId(1);
        msg.setProductSpuid(66);
        msg.setProductItemid(3);
        msg.setProductSkuid(1);
        msg.setProductPrice(69);
        msg.setRepoAmount(0);
        msg.setSettleFlag(1);
        msg.setDiscountAmount(2.66);
        return msg;
    }
}
