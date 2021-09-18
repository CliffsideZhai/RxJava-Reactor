package io.netty.example.echo;



import java.util.function.Consumer;

/**
 * @author cliffside
 * @date 2021-09-17 22:02
 */
public class PromiseDemo {


    static class Promise<T> implements Runnable{

        public Consumer<T> consumer;

        @Override
        public void run() {
            System.out.println("zhixing");
            Object o =1 ;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                if (consumer!=null){
                    consumer.accept((T) o);
                }
            }
        }
    }


    public static void main(String[] args) {
        Promise<Integer> integerPromise = new Promise<>();
        Thread thread = new Thread(integerPromise);
        thread.start();
    }
}
