//package util;
//
///**
// * @author 911
// * @date 2020-11-20 11:34
// */
//
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.channels.Channels;
//import java.nio.channels.FileChannel;
//import java.nio.channels.ReadableByteChannel;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class HttpDownloader implements Callable<String> {
//    URLConnection connection;
//    FileChannel outputChann;
//    public static volatile int count = 0;
//
//    public static void main(String[] args) throws Exception {
//
//        ExecutorService poll = Executors.newFixedThreadPool(100);
//
//        for (int i = 0; i < 100; i++) {
//            Calendar now = Calendar.getInstance();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String fileName = sdf.format(new Date()) +".txt";
//            poll.submit(new HttpDownloader("http://www.sina.com",
//                    (new FileOutputStream(fileName)).getChannel()));
//        }
//
//        poll.shutdown();
//
//        long start = System.currentTimeMillis();
//        while (!poll.isTerminated()) {
//            Thread.sleep(1000);
//            System.out.println("已运行"
//                    + ((System.currentTimeMillis() - start) / 1000) + "秒，"
//                    + HttpDownloader.count + "个任务还在运行");
//        }
//    }
//
//    public HttpDownloader(String url, FileChannel fileChannel) throws Exception {
//        synchronized (HttpDownloader.class) {
//            count++;
//        }
//        connection = (new URL(url)).openConnection();
//        this.outputChann = fileChannel;
//    }
//
//    @Override
//    public String call() throws Exception {
//        connection.connect();
//        InputStream inputStream = connection.getInputStream();
//        ReadableByteChannel rChannel = Channels.newChannel(inputStream);
//        outputChann.transferFrom(rChannel, 0, Integer.MAX_VALUE);
//        // System.out.println(Thread.currentThread().getName() + " completed!");
//        inputStream.close();
//        outputChann.close();
//        synchronized (HttpDownloader.class) {
//            count--;
//        }
//        return null;
//    }
//}
