package top.cliffside.RxjavaDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author cliffside
 * @date 2021-06-02 10:54
 */
@RestController
public class MainController {

    @GetMapping("/rxjava")
    public Mono<String> get(){
        System.out.println("----------111");

        Mono<String> result = Mono.create(sink -> getResult());
        System.out.println("222-------------");
        System.out.println(result);
        return result;
    }

    private String getResult(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello,spring!";
    }
}
