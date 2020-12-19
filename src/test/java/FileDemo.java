import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDemo {

    //1.复制文件夹


    public static void copyDir(String sourcePath, String newPath) throws IOException {
        File file = new File(sourcePath);
        String[] filePath = file.list();

        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {
            if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
                copyDir(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }

            if (new File(sourcePath + file.separator + filePath[i]).isFile()) {
                copyFile(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            }

        }
    }

    //2. 复制文件的方法


    public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);

        byte[] buffer = new byte[2097152];
        int readByte = 0;
        while ((readByte = in.read(buffer)) != -1) {
            out.write(buffer, 0, readByte);
        }

        in.close();
        out.close();
    }

    //


    public static void main(String[] args) throws IOException {
        File file1=new File("F:\\0601\\");
        getDirectory(file1);
    }


    public static void fileMove(String from, String to) throws Exception {
        try {
            File dir = new File(from);
            File[] files = dir.listFiles();
            if (files == null) return;
            File moveDir = new File(to);
            if (!moveDir.exists()) {
                moveDir.mkdirs();
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    fileMove(files[i].getPath()
                            ,to + "\\" + files[i].getName());
                    files[i].delete();
                }
                File moveFile =
                        new File(moveDir.getPath() + "\\"
                                + files[i].getName());
                if (moveFile.exists()) {
                    moveFile.delete();
                }
                files[i].renameTo(moveFile);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private static void getDirectory(File file) {

        File flist[] = file.listFiles();//建立一个文件
        if (flist == null || flist.length == 0) {
//            System.out.println("文件为空");
        }
        for (File f : flist) {
            if (f.isDirectory()) {
                //这里将列出所有的文件夹
//                System.out.println("Dir==>" + f.getAbsolutePath());
                getDirectory(f);//这里是递归调用的代码,如果该文件夹还有目录,则继续调用该方法
            } else {
                //这里将列出所有的文件
                System.out.println("file==>" + f.getAbsolutePath());
                moveFile(f.getAbsolutePath(),"F:\\123\\");
            }
        }
    }

    public static void moveFile(String srcFullPath,String target){
        File afile = new File(srcFullPath);
        if (afile.renameTo(new File(target + afile.getName()))) {
            System.out.println("File is moved successful!");
        } else {
            System.out.println("File is failed to move!");
        }
    }
}
