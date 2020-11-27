package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 911
 * @date 2020-11-16 09:00
 */
public class VideoUtil {

    private static String exePath ="D:\\sdk\\ffmpeg\\bin\\ffmpeg.exe";
    private static int number =2;
    private static String start ="00:00:00";
    private static String end ="01:00:00";

    public static void changeAmrToMp3(String sourcePath, String targetPath)  {

        String webroot = "D:\\test\\ffmpeg\\bin";

        Runtime run = null;

        try {

            run = Runtime.getRuntime();

            long start = System.currentTimeMillis();

            System.out.println(new File(webroot).getAbsolutePath());

            //执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame

            //wav转pcm

//            Process p = run.exec(new File(webroot).getAbsolutePath() + "/ffmpeg -y -i " + sourcePath + " -acodec pcm_s16le -f s16le -ac 1 -ar 16000 " + targetPath);
            //ffmpeg -i foo.mp4 foobar.mp3
            String cmd =new File(webroot).getAbsolutePath() + " /ffmpeg  -i " + sourcePath + " " + targetPath;
            System.out.println(cmd.replace("\\","/"));
            Process p = run.exec(cmd.replace("\\","/"));

            p.getOutputStream().close();

            p.getInputStream().close();

            p.getErrorStream().close();

            p.waitFor();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            //run调用lame解码器最后释放内存

            run.freeMemory();

        }

    }

    private static List<String> getVideoInfo(String inputPath) {
        List<String> commend = new java.util.ArrayList<String>();
        commend.add(exePath);
        commend.add("-i");
        commend.add(inputPath);

        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.redirectErrorStream(true);
            Process p = builder.start();

            // 1. start
            BufferedReader buf = null; // 保存ffmpeg的输出结果流
            String line = null;
            // read the standard output

            buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            List<String> list = new ArrayList<>();
            while ((line = buf.readLine()) != null) {
//                System.out.println(line);
                list.add(line);
//                continue;
            }
            p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
            // 1. end
            return list;
        } catch (Exception e) {
            // System.out.println(e);
            return null;
        }
    }

    public static String getVideoLength(String inputPath){
        List<String> list = getVideoInfo(inputPath);
        String result ="";
        for (int i = 0; i < list.size(); i++) {
            String info = list.get(i);
            if(info.contains("Duration") && info.contains("start")){
                result = info;
                break;
            }
        }
        return result.split(",")[0].split(": ")[1];
    }


    // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
    private static void splitVideo(String inputPath,int number) {
        String length = getVideoLength(inputPath);
        File file = new File(inputPath);
        String name = file.getName();
        String path = file.getParent();
        String type = name.split("\\.")[1];
        StringBuilder sb = new StringBuilder();
        //需要配置环境变量
        for (int i = 0; i <number ; i++) {
            String fileName = name.replace("."+type,"_"+(i+1)+"."+type);
            if(i==0){
                sb.append("ffmpeg -ss 00:00:00 -t 01:00:00 -i '.\\"+name+"' -vcodec copy -acodec copy '.\\"+fileName+"'\n");
            }else if(i==1){
                sb.append("ffmpeg -ss 01:00:00 -t "+length+" -i '.\\"+name+"' -vcodec copy -acodec copy '.\\"+fileName+"'\n");
            }
        }
        for (int i = 0; i <2 ; i++) {
            String fileName = name.replace("."+type,"_"+(i+1)+"."+type);
            String fileName2 = name.replace("."+type,"_"+(i+1)+".mp3");
            sb.append("ffmpeg -i '.\\"+fileName+"' -b:a 128k '.\\"+fileName2+"'\n");
        }
        System.out.println(sb.toString());
        TxtUtil.writeTxt(path+"\\cmd.bat",sb.toString());
    }


    public static void main(String[] args) throws Exception  {
        String sourceSrc = "D:\\BaiduNetdiskDownload\\五：微服务专题\\1.微服务入门&Nacos实战\\SpringCloudNacos精讲.mp4";
        System.out.println(getVideoLength(sourceSrc));
        splitVideo(sourceSrc,number);

        String[] cmd = new String[5];
        String url = "D:\\BaiduNetdiskDownload\\五：微服务专题\\1.微服务入门&Nacos实战";
        cmd[0] = "cmd";
        cmd[1] = "/c";
        cmd[2] = "start";
        cmd[3] = " ";
        cmd[4] = url;
        Runtime.getRuntime().exec(cmd);

//        String targetSrc = "C:\\Users\\xiala\\Desktop\\123\\Mysql MVCC底层原理详解.mp3";
//        changeAmrToMp3(sourceSrc, targetSrc);
    }
}
