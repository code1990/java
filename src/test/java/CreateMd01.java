import org.junit.Test;
import util.TxtUtil;

import java.util.List;

/**
 * @author 911
 * @date 2020-09-09 08:56
 *
 * 4. 输入以下命令，即可自动生成目录。
pandoc -s --toc --toc-depth=4 FAQ.md -o FAQ.md

注：pandoc 默认生成三级目录。以上述命令为例，如果使用如下命令则只会生成三级目录：
pandoc -s --toc FAQ.md -o FAQ.md

而我想让 FAQ.md 这篇文档生成四级目录，所以加了个参数 --toc-depth，并将其值设置为 4。大家可根据具体需求进行设置。


 */
public class CreateMd01 {
    @Test
    public void getInfo123(){
        String path ="C:\\Users\\xiala\\Desktop\\124.txt";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <list.size() ; i++) {
            String str = list.get(i);
            if(str.contains("·")){
                //+表示多个
                str = str.replaceAll("\\·+"," ");
            }
            String[] strArray = str.split(" ");
            if(strArray.length==3){
                str = strArray[0]+" "+strArray[1];
            }else{
//                str = getArrayStr(strArray);
            }
            str = str.trim();
            if (str.contains("小结")||str.contains("习题")){
                continue;
            }
            if(str.contains("章")){
                str ="## "+str;
            }else{
                if (str.split("\\.").length==2){
                    str ="### "+str+"\n";
                }else {
                    str ="#### "+str+"\n";
                }
                String type ="F#";
                if(true){
                    str = str+"```"+type+"\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "```";
                }
            }
            sb.append(str+"\n");
            System.out.println(str);
        }
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\125.md",sb.toString());
    }

//    @Test
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

    public String getArrayStr(String[] array){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <array.length-1 ; i++) {
            String str = array[i];
            if(i==0){
                str=str+" ";
            }
            sb.append(str);
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    @Test
    public void getInfo111(){
        String path ="D:\\spider\\123.txt";
        String path2 ="D:\\spider\\1234.txt";
        String path3 ="D:\\spider\\city.txt";
        List<String> list = TxtUtil.readTxt(path);
        List<String> list3 = TxtUtil.readTxt(path3);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <list.size() ; i++) {
            boolean flag = false;
            for (int j = 0; j <list3.size() ; j++) {
                System.out.println(list3.get(j));
                if(list.get(i).toLowerCase().contains(list3.get(j).toLowerCase())){
                    flag= true;
                    break;
                }
            }
            if(!flag){
                sb.append(list.get(i)+"\n");
            }
            System.out.println(list.get(i));
        }
        TxtUtil.writeTxt(path2,sb.toString());
    }
}
