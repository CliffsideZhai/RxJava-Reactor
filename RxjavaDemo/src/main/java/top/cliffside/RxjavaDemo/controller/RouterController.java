package top.cliffside.RxjavaDemo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpCookie;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import top.cliffside.RxjavaDemo.pojo.Person;

import java.util.HashMap;
import java.util.Optional;

/**
 * @author cliffside
 * @date 2021-06-08 16:02
 */
public class RouterController {

}


/**
 * 配置类，通过URI对应的handler
 * 匹配uri的规则，包括请求方法，put/post
 *
 */
@Configuration
class FluxRouter{
    //每一个router对应一个handler
    //每一个handler对应一个处理器
    @Bean
    public RouterFunction<ServerResponse> routerFlux(FluxHandler handler){
        return RouterFunctions
                //匹配规则，
                .route(RequestPredicates.path("/001")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        serverRequest -> ServerResponse.ok().body(BodyInserters.fromValue("testing")))
                .andRoute(RequestPredicates.GET("/handler"),
                        //.and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                        handler::handler1)
                .andRoute(RequestPredicates.GET("/json"),
                        handler::handler24json)
                .andRoute(RequestPredicates.GET("/getParams"),
                        handler::handler24params)
                .andRoute(RequestPredicates.GET("/getParams/{id}/{name}"),
                        handler::handler24rest);
    }
}

/**
 * 单例对象，但是可能线程不安全
 */
@Component
class FluxHandler{

    public Mono<ServerResponse> handler1(ServerRequest request){
        //业务逻辑
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("正在测试handler1"));
    }

    public Mono<ServerResponse> handler24json(ServerRequest request){

        Person person = new Person();
        person.setId(1);
        person.setName("alibaba");

        //业务逻辑
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(person));
    }

    public Mono<ServerResponse> handler24params(ServerRequest request){
        MultiValueMap<String, HttpCookie> cookies = request.cookies();

        Optional<String> id = request.queryParam("id");
        System.out.println("id"+id.get());
        //业务逻辑
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(cookies));
    }

    public Mono<ServerResponse> handler24rest(ServerRequest request){
        String id = request.pathVariable("id");
        String name = request.pathVariable("name");
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("id",id);
        stringStringHashMap.put("name",name);
        //业务逻辑
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(stringStringHashMap));
    }
}