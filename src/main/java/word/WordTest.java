package word;

import com.alibaba.fastjson.JSON;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.junit.Test;

import java.util.List;

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
}
