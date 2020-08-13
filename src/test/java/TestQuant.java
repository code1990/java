import org.junit.Test;
import util.TxtUtil;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 911
 * @date 2020-07-01 08:56
 */
public class TestQuant {

    @Test
    public void getInfo01() throws Exception{
        String path = "C:\\Users\\xiala\\Desktop\\test123\\test111.txt";
        String root = "D:\\github\\quant\\";
        List<String> list = TxtUtil.readTxt(path);
        for (int i = 0; i <list.size() ; i++) {
            String str = list.get(i);
            System.out.println();
            String info = root+"0"+(i+1)+str;
            new File(info).mkdirs();
            new File(info+"\\readme.md").createNewFile();
        }
    }

    @Test
    public void getInfo02()throws Exception{
        String path = "C:\\Users\\xiala\\Desktop\\test123\\test111.txt";
        String root = "D:\\github\\quant\\01股票书籍";
        List<String> list = TxtUtil.readTxt(path);
        Collections.reverse(list);
        for (int i = 0; i <list.size() ; i++) {
            String str = list.get(i);
            String number =""+(i+1);
            if(i+1<10){
                number="00"+(i+1);
            }else if(i+1<100){
                number="0"+(i+1);
            }
            String info = root+"\\"+number+""+str+".md";
            System.out.println(info);
            new File(info).createNewFile();
//            new File(info).mkdirs();
        }
    }

    @Test
    public void getInfo03(){
        String path ="http://www.gucuan.com/lbyrm/list_16_7.html";
        for (int i = 1; i <81 ; i++) {
            System.out.println(path.replace("7",i+""));
        }
    }

    @Test
    public void getInfo04(){
        String path ="C:\\Users\\xiala\\Desktop\\111.txt";
        List<String> list = TxtUtil.readTxt(path);
        for (int i = 0; i <list.size() ; i++) {
            String str = replaceSpecStr(list.get(i));
            System.out.println(str);
//            if(str.contains("\t")){
//                str = str.split("\t")[0];
//            }
//            System.out.println(str);
//            if(!str.startsWith("2")){
//                System.out.println("           "+str);
////                System.out.println(str.split(" ").length);
////                if(str.split(" ").length>2){
////
////                }
////
//            }else{
//                System.out.println(str);
//            }
//            int length = "           ".length();
//            System.out.println(str.substring(length,str.length()));
        }


    }

    public static String replaceSpecStr(String orgStr){
        if (null!=orgStr&&!"".equals(orgStr.trim())) {
            String regEx="[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(orgStr);
            return m.replaceAll("");
        }
        return null;
    }
}
