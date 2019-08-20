import org.junit.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author issuser
 * @date 2019-08-20 22:02
 */
public class IOTest {
    /*1.流失一组有序的数据序列 IO通常与磁盘文件存取有关

    字节流以Stream结尾 字符流以reader writer结尾
    * */

    /*2.文件/文件夹的创建,删除*/
    @Test
    public void testFile() {
        /*所在项目的跟路径*/
        String path = "word.txt";
        String dir = "hello";
        File file = new File(path);
        File dirFile = new File(dir);
        try {
            file.createNewFile();
            dirFile.mkdirs();
            System.out.println(file.getAbsolutePath());
            System.out.println(file.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            file.delete();
            dirFile.delete();
        }
    }

    /*3.获取文件的详细信息*/
    @Test
    public void testGetFileInfo() {
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\hosts"));//构造方法1
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\", "hosts"));//构造方法2
        System.out.println(new File(new File("C:\\Windows\\System32\\drivers\\etc\\"), "hosts"));//构造方法3
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\hosts").getName());//获取文件的名称
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\hosts").canRead()); //判断文件是否为可读的
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\hosts").canWrite());//判断文件是否可被写入
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\hosts").exists());//判断文件是否存在
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\hosts").length()); //获取文件的长度(以字节为单位);//
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\hosts").getAbsolutePath());//获取文件的绝对路径
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\hosts").getParent());//获取文件的父路径
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\hosts").isFile()); //判断文件是否存在
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\hosts").isDirectory()); //判断文件是否为一个目录
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\hosts").isHidden()); //判断文件是否为隐藏文件
        System.out.println(new File("C:\\Windows\\System32\\drivers\\etc\\hosts").lastModified());//获取文件最后修改时间

    }

    /*4.文件输入流 输出流*/
    @Test
    public void testFileInputOutputStream() throws Exception {
        /*创建一个文件往文件写入内容 然后读取文件内容 打印到控制台*/
        File file = new File("word.txt");
        FileOutputStream os = new FileOutputStream(file);
        byte[] bytes = "Hello Java,Hello World".getBytes();
        /*方法：将指定的字节写入此输出流。*/
        os.write(bytes);
        os.flush();//方法：彻底完成输出并清空缓存区。
        os.close();//方法：关闭输出流。
        FileInputStream in = new FileInputStream(file);
        byte[] bytes1 = new byte[1024];
        int length = in.read(bytes1);
        System.out.println(new String(bytes1, 0, length));
        in.close();//方法关闭此输入流并释放与该流关联的所有系统资源。
        file.delete();
    }

    /*5.文件字符流 可以避免中文乱码*/
    @Test
    public void testFileWriterReader() throws Exception {
        File file = new File("word.txt");
        FileWriter fw = new FileWriter(file);
        String str = "人工智能物联网大数据";
        fw.write(str);
        fw.flush();
        fw.close();
        FileReader fr = new FileReader(file);
        char[] bytes1 = new char[1024];
        int length = fr.read(bytes1);
        System.out.println(new String(bytes1, 0, length));
        fr.close();//方法关闭此输入流并释放与该流关联的所有系统资源。
        file.delete();
    }

    /*6.缓冲流是高级流,对于低级流的封装 弥补性能的不足*/
    /*BuffedInputStream BufferOutputStream
     * BufferedReader BufferredWriter*/
    @Test
    public void testBuffer() throws Exception {
        String[] strArray = {"Hadoop", "Hive", "HBase", "Spark"};
        File file = new File("word.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String str : strArray) {
            bw.write(str);
            bw.newLine();/*换行*/
        }
        bw.close();
        fw.close();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String str = null;
        while ((str = br.readLine()) != null) {
            System.out.println(str);
        }
        br.close();
        fr.close();
        file.delete();
    }

    /*7.DataInputStream/DataOutputStream在读取数据时候不关心数据的字节*/
    @Test
    public void testDataInputOutputStream() throws Exception {
        File file = new File("word.txt");
        FileOutputStream fos = new FileOutputStream(file);
        DataOutputStream dos = new DataOutputStream(fos);
        //3种写入字符串的方法 writeChars  writeBytes writeUTF
        dos.writeUTF("writeUTF");
        dos.close();
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        //writeUTF readUTF必须一致否则报错
        System.out.println(dis.readUTF());
        file.delete();
    }

    /*文件压缩 不支持中文文件目录的问题*/
    @Test
    public void testUnZip() throws Exception {


    }

    /*文件解压*/
}
