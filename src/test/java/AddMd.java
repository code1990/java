import org.junit.Test;

import java.io.*;

/**
 * 4. 输入以下命令，即可自动生成目录。
 pandoc -s --toc --toc-depth=4 FAQ.md -o FAQ.md

 注：pandoc 默认生成三级目录。以上述命令为例，如果使用如下命令则只会生成三级目录：
 pandoc -s --toc FAQ.md -o FAQ.md

 而我想让 FAQ.md 这篇文档生成四级目录，所以加了个参数 --toc-depth，并将其值设置为 4。大家可根据具体需求进行设置。


 */
public class AddMd {
    @Test
    public void getInfo(){
//        String path = "D:\\github\\java\\src\\main\\java\\";
        String path = "D:\\github\\hadoop\\src\\main\\java\\";
        for(File f:new File(path).listFiles()){
            boolean flag = false;
            for(File f2:f.listFiles()){
                if(!f2.getName().contains(".md")){
                    flag=true;
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

    public static void main(String[] args) {
        FileOutputStream outputStream = null;
        InputStream inputStream = null;

        int temp = 0;
        try {
            File file1 = new File("C:\\Users\\xiala\\Desktop\\Mysql MVCC底层原理详解.mp3");
            File file2 = new File("C:\\Users\\xiala\\Desktop\\111.mp3");
            inputStream = new FileInputStream(file1);
            outputStream = new FileOutputStream(file2);
            // 原歌曲长度
            System.out.println("file1--size:"+file1.length());  //2633786
            // 截取2分16秒,原歌曲长2分44秒     136/164*2633786
            byte[] bytes = new byte[2180000];
//            temp = inputStream.read(bytes);
//            outputStream.write(bytes, 0, temp);
//            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                inputStream.close();
                outputStream.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
