package word;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.junit.Test;
import util.TxtUtil;

import java.util.List;

public class JieBaWordTest {
    private JiebaSegmenter segmenter = new JiebaSegmenter();
    String sentences = "北京京天威科技发展有限公司大庆车务段的装车数量";
    @Test
    public void testCutForSearch() {
        System.out.println(segmenter.sentenceProcess(sentences));
    }

    @Test
    public void getInfo(){
        String path = "C:\\Users\\xiala\\Desktop\\cpp.txt";
        List<String> list = TxtUtil.readTxt(path);
        for (int i = 0; i <list.size() ; i++) {
            String str = list.get(i).split(",")[0];
            System.out.println(str);
        }
    }
}
