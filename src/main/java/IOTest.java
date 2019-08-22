import org.junit.Test;

import java.io.*;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;


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
        String path = "C:\\Windows\\System32\\drivers\\etc\\hosts";
        String path2 = "C:\\Windows\\System32\\drivers\\etc\\";
        System.out.println(new File(path));//构造方法1
        System.out.println(new File(path2, "hosts"));//构造方法2
        System.out.println(new File(new File(path2), "hosts"));//构造方法3
        System.out.println(new File(path).getName());//获取文件的名称
        System.out.println(new File(path).canRead()); //判断文件是否为可读的
        System.out.println(new File(path).canWrite());//判断文件是否可被写入
        System.out.println(new File(path).exists());//判断文件是否存在
        System.out.println(new File(path).length()); //获取文件的长度(以字节为单位);//
        System.out.println(new File(path).getAbsolutePath());//获取文件的绝对路径
        System.out.println(new File(path).getParent());//获取文件的父路径
        System.out.println(new File(path).isFile()); //判断文件是否存在
        System.out.println(new File(path).isDirectory()); //判断文件是否为一个目录
        System.out.println(new File(path).isHidden()); //判断文件是否为隐藏文件
        System.out.println(new File(path).lastModified());//获取文件最后修改时间

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

    /*文件压缩  原生不支持中文文件目录*/
    @Test
    public void testZipFile() throws Exception {
        unZip("cronCreator.zip");
        zip("cronCreator.zip");
    }


    //解压指定zip文件
    public static void unZip(String str) {//unZipfileName需要解压的zip文件名
        FileOutputStream fos;
        File file;
        InputStream inputStream;

        try {
            ZipFile zipFile = new ZipFile(new File(str), "GBK");

            for (Enumeration entries = zipFile.getEntries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();

                String tmp = str.substring(0, str.lastIndexOf("."));
                if (!new File(tmp).exists()) {
                    new File(tmp).mkdirs();
                }
                file = new File(tmp + File.separator + entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    //如果指定文件的目录不存在,则创建之.

                    File parent = new File(file.getParent());
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }

                    inputStream = zipFile.getInputStream(entry);

                    fos = new FileOutputStream(file);
                    int readedBytes;
                    byte[] buf = new byte[1024 * 1024 * 1024];
                    while ((readedBytes = inputStream.read(buf)) > 0) {
                        fos.write(buf, 0, readedBytes);
                    }
                    fos.close();

                    inputStream.close();
                }
            }
            zipFile.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static File zip(String filePath) throws Exception {
        File target = null;
        File source = new File(filePath);
        if (source.exists()) {
            // 压缩文件名=源文件名.zip
            String zipName = source.getName() + ".zip";
            target = new File(source.getParent(), zipName);
            if (target.exists()) {
                target.delete(); // 删除旧的文件
            }
            FileOutputStream fos = null;
            ZipOutputStream zos = null;
            try {
                fos = new FileOutputStream(target);
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
                // 添加对应的文件Entry
                addEntry("/", source, zos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                zos.closeEntry();
                zos.close();
                fos.close();

            }
        }
        return target;
    }

    private static void addEntry(String base, File source, ZipOutputStream zos)
            throws IOException {
        // 按目录分级，形如：/aaa/bbb.txt
        String entry = base + source.getName();
        if (source.isDirectory()) {
            for (File file : source.listFiles()) {
                // 递归列出目录下的所有文件，添加文件Entry
                addEntry(entry + "/", file, zos);
            }
        } else {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                byte[] buffer = new byte[1024 * 10];
                fis = new FileInputStream(source);
                bis = new BufferedInputStream(fis, buffer.length);
                int read = 0;
                zos.putNextEntry(new ZipEntry(entry));
                while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, read);
                }
                zos.closeEntry();
            } finally {
                bis.close();
                fis.close();
            }
        }
    }
}
