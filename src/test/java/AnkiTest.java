import org.junit.Test;
import util.TxtUtil;

import java.io.File;
import java.util.List;

public class AnkiTest {

    @Test
    public void getInfo111(){

        List<String> list = TxtUtil.readTxt(new File("anki.txt"));

        String title ="04.SQL索引优化__";
        for (int i = 0; i <list.size() ; i++) {
            String num = i+"";
            if(i<9){
                num = "0"+(i+1);
            }
            System.out.println(title+num);
            System.out.println(list.get(i));
        }

    }

    @Test
    public void getInfo1111(){
        String  path ="D:\\BaiduNetdiskDownload\\图灵第三期vip\\三、性能调优专题";
        File[] files = new File(path).listFiles();
        for (int i = 0; i <files.length ; i++) {
            System.out.println(files[i].getName());
        }
    }
}
