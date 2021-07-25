package com.example.springreactor.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 观察者模式 observer
 * @author cliffside
 * @date 2021-05-17 19:56
 */
@RestController
public class FirstController {
    private static Log log = LogFactory.getLog(FirstController.class);

    @RequestMapping(value = "/sse",produces = "text/event-stream;charset=utf-8")
    public StringBuilder tset(){

        log.info("在准备响应式SSE");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("data:i am cliff \n\n");
        return stringBuilder;
    }
}
