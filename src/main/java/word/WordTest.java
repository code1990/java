package word;

import com.alibaba.fastjson.JSON;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.junit.Test;
import util.MapUtil;
import util.TxtUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordTest {

    @Test
    public void getInfo() {
        List<Word> words = WordSegmenter.segWithStopWords("我国工人阶级和广大劳动群众要更加紧密地团结在党中央周围");
        System.out.println(words);
        String title = "我叫李太白，我是一个诗人，我生活在唐朝";
        //移除停用词进行分词
        List<Word> list = WordSegmenter.seg(title);

        System.out.println(JSON.toJSONString(list));

        //保留停用词
        List<Word> lists = WordSegmenter.segWithStopWords(title);
        System.out.println(JSON.toJSONString(lists));
    }

    public static List<Word> getWord(String title) {
        return WordSegmenter.seg(title);
    }


    @Test
    public void getInfo1(){
        String path = "C:\\Users\\xiala\\Desktop\\new 6.txt";
        List<String> list = TxtUtil.readTxt(path);
        Map<String,Integer> map = new HashMap<String,Integer>();
        for (int i = 0; i <list.size() ; i++) {
            String str =list.get(i);
//            str = str.replaceAll("；","\n");
//            str = str.replaceAll("：    ","\n");
//            str = str.replaceAll("。","\n");
//            str = str.replaceAll("：","\n");
//            str = str.replaceAll(":","\n");
//            str = str.replaceAll(",","\n");
//            if(str.contains("工程师")){
//                continue;
//            }
//            if(str.contains("分布")){
//                System.out.println(str.replace("=======================================>",""));
//            }else{
//                if(Pattern.matches(".*[A-Za-z]+.*", str)){
//                    System.out.println(str);//true
//                }else{
////                    System.out.println("=======================================>"+str);
//                }
//            }

//            System.out.println(str.trim());
            List<Word> list2 = WordSegmenter.seg(str.trim());
            for(Word word:list2){
                String key = word.getText();
                if(map.get(key)==null){
                    map.put(key,1);
                }else{
                    map.put(key,map.get(key)+1);
                }
//                System.out.println(word.getText());
            }
        }
        MapUtil.sortDesc(map);
    }

    /**
     * 使用正则表达式来判断字符串中是否包含字母
     * @param str 待检验的字符串
     * @return 返回是否包含
     * true: 包含字母 ;false 不包含字母
     */
    public boolean judgeContainsStr(String str) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m=Pattern.compile(regex).matcher(str);
        return m.matches();
    }
}
