package top.cliffside.RxjavaDemo.test;

import reactor.core.publisher.Flux;

/**
 * @author cliffside
 * @date 2021-06-02 10:32
 */
public class FluxTest {
    public static void main(String[] args) {
        // 静态方法生成Flux


        String[] s = new String[] {"hello","flux"};
        // just 已知元素数量和内容 使用
        // 发布者flux1 就是 被观察者
        Flux<String> flux1 = Flux.just(s);

        //println 订阅者 观察者
        flux1.subscribe(System.out::println);

        Flux<String> flux2 = Flux.just("hello","flux");
        flux2.subscribe(System.out::println);


    }
}
