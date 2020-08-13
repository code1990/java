package webmagic;

/**
 * @author 911
 * @date 2020-08-04 14:14
 */
public class  TicketWindow implements Runnable{
    private int tickets = 10;//车票总量
    @Override
    public void run(){
        while(true){
            synchronized (this) {
                if(tickets>0){
                    System.out.println(Thread.currentThread().getName() + "准备出票,剩余票数:" + tickets + "张");
                    tickets--;
                    System.out.println(Thread.currentThread().getName() + "卖出一张,剩余票数:" + tickets + "张");
                    try {
                        //休眠100ms卖票完会报错ERROR: JDWP Unable to get JNI 1.2 environment, jvm->GetEnv() return code = -2JDWP exit error AGENT_ERROR_NO_JNI_ENV(183):  [../../../src/share/back/util.c:820]
                        //Thread.sleep(100);
                        Thread.sleep(500);//出票成功后让当前售票窗口睡眠,以便让其他售票窗口卖票
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println(Thread.currentThread().getName() + "余票不足,停止售票!");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        TicketWindow tw = new TicketWindow();
        for(int i=1; i<4; i++){
            Thread t = new Thread(tw,"TickWindow-" + i);
            t.start();
        }
    }

}
