import org.junit.Test;
import util.TxtUtil;

import java.util.List;

/**
 * @author 911
 * @date 2020-09-09 08:56
 */
public class CreateMd01 {
    @Test
    public void getInfo123(){
        String path ="C:\\Users\\xiala\\Desktop\\124.txt";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <list.size() ; i++) {
            String[] strArray = list.get(i).split(" ");
            String str = strArray[0]+" "+strArray[1];
            if (str.contains("小结")){
                continue;
            }
            if(str.contains("章")){
                str ="## "+str;
            }else{
                if (str.split("\\.").length==1){
                    str ="### "+str;
                }else {
                    str ="#### "+str+"\n\n";
                    String type ="kotlin";
                    if(true){
                        str = str+"```"+type+"\n" +
                                "\n" +
                                "\n" +
                                "\n" +
                                "```";
                    }
                }
            }
            sb.append(str+"\n");
            System.out.println(str);
        }
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\125.md",sb.toString());
    }

    @Test
    public void getInfo1123(){
        String path ="D:\\github\\gitee\\数学800.txt";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i).contains("部分")||list.get(i).contains("微模考")){
                continue;
            }
            System.out.println(list.get(i));
//            String[] strArray = list.get(i).split(" ");
            String str =list.get(i);// strArray[0]+" "+strArray[1];
            if(str.contains("章")){
                str ="## "+str;
            }else{
                if(str.contains("节")){
                    str ="### "+str+"\n";
                }else{
                    str ="#### "+str+"\n\n\n\n";
                }

            }
            sb.append(str+"\n");
//            System.out.println(str);
        }
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\125.md",sb.toString());
    }
}
