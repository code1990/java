package helloworld; /**
 * @author issuser
 * @date 2019-09-11 14:14
 */

import java.util.concurrent.TimeUnit;

public class TestSynchronized {

    public static void main(String[] args) {
        /*
        * 一个对象里面有多个synchronized修饰的方法 其中的一个线程调用 其他的线程只能等待
        * 锁定的对象是this  是当前的class
        * */
        new Thread(() -> {
            try{
                new Entity().getMethod();
            }catch (Exception e){
                e.printStackTrace();
            }
        }, "aa").start();
        new Thread(() -> {
            try{
                new Entity().getMethod();
            }catch (Exception e){
                e.printStackTrace();
            }
        }, "aa").start();
    }

}

class Entity {

    public static synchronized void getStaticSyncMethod() throws Exception {
        TimeUnit.SECONDS.sleep(4);
        System.out.println("getStaticSyncMethod");
    }

    public synchronized void getSyncMethod() {
        System.out.println("getSyncMethod");
    }

    public void getMethod() {
        System.out.println("getMethod");
    }
}
