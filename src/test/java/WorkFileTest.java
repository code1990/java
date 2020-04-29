import org.junit.Test;
import util.TxtUtil;

import java.util.List;

public class WorkFileTest {
    private final String admin = System.getProperty("user.name");
    private final String path = "C:\\Users\\" + admin + "\\Desktop\\1.txt";
    List<String> list = TxtUtil.readTxt(path);

    @Test
    public void getInfo() {
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            System.out.println(name.trim());
        }
    }
}
