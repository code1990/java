package word;

import org.junit.Test;

public class WordTest {

    @Test
    public void getInfo(){
        List<Word> words = WordSegmenter.segWithStopWords("我国工人阶级和广大劳动群众要更加紧密地团结在党中央周围");
        System.out.println(words);
    }
}
