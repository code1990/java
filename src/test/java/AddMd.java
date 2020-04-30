import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class AddMd {
    @Test
    public void getInfo(){
        String path = "D:\\github\\java\\src\\main\\java\\";
        for(File f:new File(path).listFiles()){
            boolean flag = false;
            for(File f2:f.listFiles()){
                if(!f2.getName().contains(".md")){
                    flag=true;
                    break;
                }
                System.out.println(path+f.getName());
//                System.out.println(f2.getName());
            }
            if(flag){
                try {
                    new File(path+f.getName()+"\\readme.md").createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
