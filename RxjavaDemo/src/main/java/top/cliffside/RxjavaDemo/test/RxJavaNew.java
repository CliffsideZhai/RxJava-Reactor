package top.cliffside.RxjavaDemo.test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author cliffside
 * @date 2021-06-02 10:15
 */
public class RxJavaNew {
    public static void main(String[] args) throws InterruptedException {
        //被观察者
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("send 1"+Thread.currentThread().getName());
                emitter.onNext("1");
                System.out.println("send 2"+Thread.currentThread().getName());
                emitter.onNext("2");
                System.out.println("send 3"+Thread.currentThread().getName());
                emitter.onNext("3");
                emitter.onNext("4");
                emitter.onNext("5");
                emitter.onComplete();
            }
        })//表示哪个线程是观察者
                .observeOn(Schedulers.computation())
                .subscribeOn( Schedulers.computation())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        // TODO Auto-generated method stub
                        System.out.print(Thread.currentThread().getName());
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(String t) {
                        // TODO Auto-generated method stub
                        System.out.print(Thread.currentThread().getName());
                        System.out.println("onNext"+t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO Auto-generated method stub
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        // TODO Auto-generated method stub
                        System.out.print(Thread.currentThread().getName());
                        System.out.println("onComplete");
                    }

                })
        ;


        //主线程休息一会儿
        Thread.sleep(10000);

    }
}
