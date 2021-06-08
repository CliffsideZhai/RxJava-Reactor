package top.cliffside.RxjavaDemo.controller;

import io.netty.util.internal.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.cliffside.RxjavaDemo.pojo.Person;
import top.cliffside.RxjavaDemo.service.PersonService;

import javax.print.attribute.standard.Media;
import java.io.Flushable;
import java.io.IOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 注解式
 * 响应式编程提高IO密集度高 下的性能，对于计算密集度仍然无可奈何
 * @author cliffside
 * @date 2021-06-03 13:36
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    private Log log = LogFactory.getLog(PersonController.class);
    @Autowired
    PersonService personService;

    /**
     * Mono 只有 0/1 个数据序列
     * @return
     */
    @GetMapping("")
    public Mono<Object> get(){
        log.info("测试");
        //create 动态 非阻塞
        Mono<Object> objectMono = Mono.create(monoSink -> {
            //组装数据序列
            monoSink.success((Person) personService.getPerson());
        }).doOnSubscribe(subscription -> {
            //1 先调用观察者去观察数据动态 = 订阅
           log.info("接收返回的观察者数据");
        }).doOnNext(data -> {
            //2 得到数据
            log.info("data :" + data);
        }).doOnSuccess(onSuccess ->{
            //3 整体完成
            log.info("成功获得数据");
        });

        // mono在这里不一定是组装好的，有可能return到容器中是null
        // 得到一个包装 数据序列 -> 包装特征 -》 容器 拿到序列 -》 执行序列里的方法
        // 真正执行的过程完全交给了 底层容器来执行对应  ajax 的2
        // 看起来像是异步，实质上 阻塞的过程在容器内部
        return objectMono;
        /**
         * 类似 ajax a（） -> b（c()）
         * 1、 写回调接口 让b执行完来回调
         * 2、 直接传方法过去，把c传过去
         *
         */
    }

    @GetMapping("/get2")
    public Mono<Object> get2(ServerRequest webServer){

        return null;
    }

    @RequestMapping("/get3")
    //WebFlux 中特有
    public Mono<Object> get3(ServerHttpRequest httpRequest)  {
        log.info("httpRequest"+httpRequest.getHeaders());


        return Mono.just("正在更新");
    }

    @GetMapping("/get4")
    public Mono<Object> get4(String name){

        return Mono.just(name);
    }

    @GetMapping("/get5")
    public void handle(@CookieValue("JSESSIONID") String cookie) {
        //...
        log.info(cookie);
        return ;
    }

    @GetMapping("/demo")
    public Mono<Object> handle(
            @RequestHeader("Accept-Encoding") String encoding,
            @RequestHeader("Keep-Alive") long keepAlive) {
        System.out.println(encoding+keepAlive);
        return Mono.just(encoding);
    }


    @GetMapping("/get6")
    public Mono<Object> get6(WebSession session) {

        if (StringUtils.isEmpty(session.getAttribute("code"))){
            session.getAttributes().put("code",222);
        }

        log.info("code:" + session.getAttribute("code"));
        return Mono.just(1);
    }

    @GetMapping(value = "/sse",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> get7(){
        // 1. 封装对象
        Flux<Integer> integerFlux = Flux.fromStream(IntStream.range(1, 10000).mapToObj(i -> {
            return i;
        }));

        return integerFlux;
    }

}
