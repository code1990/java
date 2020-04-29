package concurrent;

/**
 * @author issuser
 * @date 2019-08-29 21:49
 * <p>
 */
public class ProviderConsumerTest {
    public static void main(String[] args) {
        Product product = new Product();
        new Provider(product).start();
        new Consumer(product).start();
    }
}

/*wait 当某个条件成立时候 notify 和notifyall 方法通知处于等待中的线程*/
class Provider extends Thread {
    private final Product product;

    public Provider(Product product) {
        this.product = product;
    }

    @Override
    public void run() {
        for (char c = 'A'; c < 'Z'; c++) {
            synchronized (product) {
                product.setChar(c);
                System.out.println(c + " produced by provider");
            }
        }
    }
}

class Consumer extends Thread {
    private final Product product;

    public Consumer(Product product) {
        this.product = product;
    }

    @Override
    public void run() {
        char ch;
        do {
            synchronized (product) {
                ch = product.getChar();
                System.out.println(ch + " consumed by consumer ");
            }
        } while (ch != 'Z');
    }
}

class Product {

    private char c;
    private volatile boolean writeable = true;

    synchronized void setChar(char c) {
        while (!writeable) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.c = c;
        writeable = false;
        notify();
    }

    synchronized char getChar() {
        while (writeable) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writeable = true;
        notify();
        return c;
    }
}
