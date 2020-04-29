package word;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.junit.Test;

public class JieBaWordTest {
    private JiebaSegmenter segmenter = new JiebaSegmenter();
    String sentences = "北京京天威科技发展有限公司大庆车务段的装车数量";
    @Test
    public void testCutForSearch() {
        System.out.println(segmenter.sentenceProcess(sentences));
    }
}
