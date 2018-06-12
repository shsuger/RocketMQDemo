package com.shsuger.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author shsuger
 * @Date 2018/5/25
 */
@Component
public class MessagePrinter {
    final private MessageService service;
    @Autowired
    public MessagePrinter(MessageService service) {
        this.service = service;
    }

    public void printMessage() {
        System.out.println(this.service.getMessage());
    }
}
