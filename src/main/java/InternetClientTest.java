import java.io.*;
import java.net.Socket;

/**
 * @author issuser
 * @date 2019-08-22 21:41
 */
public class InternetClientTest {

    public static void main(String[] args) {
        try {
            System.out.println("客户端尝试获取连接");
            Socket socket = new Socket("127.0.0.1", 8000);
            OutputStream os = socket.getOutputStream();
            os.write("我是client".getBytes());
            socket.shutdownOutput();
            InputStream is = socket.getInputStream();
            int len=0;
            byte[] bytes=new byte[1024];
            while ((len=is.read(bytes))!=-1){
                System.out.println(new String(bytes,0,len));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
