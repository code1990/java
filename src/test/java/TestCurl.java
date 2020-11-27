/**
 * @author 911
 * @date 2020-11-20 22:05
 */

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestCurl {


    public static void main(String[] args){
        String[] cmds={"curl","-H"," 'Host: api.waditu.com'","-H"," 'User-Agent: python-requests/2.24.0'","-H"," 'Accept: */*'","-H"," 'Content-Type: application/json' --data-binary"," '{\"api_name\": \"index_basic\", \"token\": \"4907b8834a0cecb6af0613e29bf71847206c41ddc3e598b9a25a0203\", \"params\": {\"market\": \"CSI\"}, \"fields\": \"\"}'","--compressed"," 'http://api.waditu.com/'"};//必须分开写，不能有空格
        System.out.println(execCurl(cmds));
    }


    public static String execCurl(String[] cmds) {
        ProcessBuilder process = new ProcessBuilder(cmds);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
//                builder.append(line);
//                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();

        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }
        return null;

    }

    @Test
    public void httpPost() {
//        CUrl curl = new CUrl("curl -H 'Host: api.waditu.com' -H 'User-Agent: python-requests/2.24.0' -H 'Accept: */*' -H 'Content-Type: application/json' --data-binary '{\"api_name\": \"index_basic\", \"token\": \"4907b8834a0cecb6af0613e29bf71847206c41ddc3e598b9a25a0203\", \"params\": {\"market\": \"CSI\"}, \"fields\": \"\"}' --compressed 'http://api.waditu.com/'");
////                .data("hello=world&foo=bar")
////                .data("foo=overwrite");
//        curl.exec();
//        assertEquals(200, curl.getHttpCode());
        String[] cmds = {"curl", "-H", "Host: www.chineseconverter.com", "-H", "Cache-Control: max-age=0", "--compressed", "https://www.chineseconverter.com/zh-cn/convert/chinese-stroke-order-tool"};
        execCurl2(cmds);

    }
        public static String execCurl2(String[] cmds){
            ProcessBuilder process = new ProcessBuilder(cmds);
            Process p;
            try {
                p = process.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    builder.append(line);
                    builder.append(System.getProperty("line.separator"));
                }
                return builder.toString();

            } catch (IOException e) {
                System.out.print("error");
                e.printStackTrace();
            }
            return null;
        }
}
