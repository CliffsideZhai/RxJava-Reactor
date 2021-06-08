package top.cliffside.RxjavaDemo.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import top.cliffside.RxjavaDemo.pojo.Person;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cliffside
 * @date 2021-06-03 13:37
 */
@Service
public class PersonService {
    //写一份假数据
    static ConcurrentHashMap<Integer,Person> map =new ConcurrentHashMap<>();
    static {
        for (int i = 0; i < 100; i++) {
            Person person = new Person();
            person.setId(i);
            person.setName("spring"+i);
            map.put(i,person);
        }
    }
    public Object getPerson() {
        return map.get(1);
    }

    public Flux<Person> getPersons(){
        return Flux.fromIterable(map.values());
    }
}
