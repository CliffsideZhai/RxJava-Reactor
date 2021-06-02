package top.cliffside.RxjavaDemo.test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 当前仍然是同步执行的情况
 * @author cliffside
 * @date 2021-06-02 10:06
 */
public class RxJavaPack {
    public static void main(String[] args) {

        //被观察者
        Observable<String> girl = Observable.create(new ObservableOnSubscribe<String>() {
            //发射
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                //无限使用，发送事件
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onNext("4");
                emitter.onNext("5");
                emitter.onComplete();
            }
        });

        // 观察者
        Observer<String> man = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                // TODO Auto-generated method stub
                System.out.println("onSubscribe" + d);
            }

            @Override
            public void onNext(String t) {
                // TODO Auto-generated method stub
                System.out.println("onNext " + t);
            }

            @Override
            public void onError(Throwable e) {
                // TODO Auto-generated method stub
                System.out.println("onError " + e.getMessage());
            }

            @Override
            public void onComplete() {
                // TODO Auto-generated method stub
                System.out.println("onComplete");
            }
        };

        girl.subscribe(man);
    }
}
