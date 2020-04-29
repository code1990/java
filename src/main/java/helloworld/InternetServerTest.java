package helloworld;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author issuser
 * @date 2019-08-22 21:23
 */
public class InternetServerTest {
    /*
    tcp协议是一种以固定连线为基础的协议 保证数据的可靠传输
    udp是无连接通信协议 不保证数据的传输
    tcp程序基础
    1.服务端创建一个serversocket调用accept方法等待客户端连接
    2.客户端创建一个socket请求与服务端连接
    3.服务端接受连接请求 同时创建一个新的socket等待其他的连接
    InetAddress类与IP地址有关的信息
    Socket套接字 ServerSocket
    serversocket类是一个封装支持TCP协议的操作类 工作在服务端接受客户端请求
    socket类 封装了一个支持tcp协议的操作类 一个socket表示一个客户端
    * */

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8000);
            System.out.println("服务端套接字创建成功,等待连接");
            Socket socket = server.accept();
            OutputStream os = socket.getOutputStream();
            os.write("服务端收到客户端消息".getBytes());
            InputStream is = socket.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            int length = 0;
            while ((length = bf.read()) != -1) {
                System.out.println((char) length);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
