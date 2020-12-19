package util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * @program: java
 * @Date: 2020-09-12 19:59
 * @Author: code1990
 * @Description:
 */
public class ImageUtil {

    /**
     * java手动抠出图像
     *
     * @param file   要抠取的图像
     * @param x      坐标 起点 X坐标
     * @param y      坐标 起点 Y 坐标
     * @param w      坐标 终点 X坐标
     * @param h      坐标 终点 Y坐标
     * @param suffix 后缀名
     * @param file   文件 生产出的图片
     * @return
     * @throws IOException
     */
    public boolean resize(File file, int x, int y, int w, int h, String suffix, File newFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(file));
        System.out.println("H轴: " + bufferedImage.getHeight());
        System.out.println("W轴: " + bufferedImage.getWidth());
        bufferedImage = bufferedImage.getSubimage(x, y, w, h);
        return ImageIO.write(bufferedImage, suffix, newFile);
    }

    @Test
    public void getInfo() throws Exception {
        String path = "C:\\Users\\admin\\Desktop\\0-00000010.jpg";
        resize(new File(path), 50, 90, 580, 850, "jpg", new File("C:\\Users\\admin\\Desktop\\0.jpg"));
    }

    //
    @Test
    public void getInfo1() throws Exception {
        String path = "C:\\Users\\admin\\Desktop\\老吕母题800练\\20_09_12_20_21_21\\";
        File file = new File(path);
        for (File file1 : file.listFiles()) {
            String name = file1.getName();
            System.out.println(name);
            // 需要区分奇数页 偶数页 边框位置不同  暂时不优化
            resize(file1, 50, 90, 580, 850, "jpg", new File
                    ("C:\\Users\\admin\\Desktop\\老吕母题800练\\math\\" + name));
        }
    }

    @Test
    public void getIf() {
        for (int i = 11; i <= 20; i++) {
            new File("C:\\Users\\admin\\Desktop\\老吕母题800练\\logic - 副本\\" + i).mkdirs();
        }
    }

    @Test
    public void test1290() throws IOException

    {
        /**
         * 要处理的图片目录
         */
        File dir = new File("d:/test/");
        /**
         * 列出目录中的图片，得到数组
         */
        File[] files = dir.listFiles();
        /**
         * 遍历数组
         */
        for (int x = 0; x < files.length; x++) {
            /**
             * 定义一个RGB的数组，因为图片的RGB模式是由三个 0-255来表示的 比如白色就是(255,255,255)
             */
            int[] rgb = new int[3];
            /**
             * 用来处理图片的缓冲流
             */
            BufferedImage bi = null;
            try {
                /**
                 * 用ImageIO将图片读入到缓冲中
                 */
                bi = ImageIO.read(files[x]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /**
             * 得到图片的长宽
             */
            int width = bi.getWidth();
            int height = bi.getHeight();
            int minx = bi.getMinX();
            int miny = bi.getMinY();
            System.out.println("正在处理：" + files[x].getName());
            /**
             * 这里是遍历图片的像素，因为要处理图片的背色，所以要把指定像素上的颜色换成目标颜色
             * 这里 是一个二层循环，遍历长和宽上的每个像素
             */
            for (int i = minx; i < width; i++) {
                for (int j = miny; j < height; j++) {
                    // System.out.print(bi.getRGB(jw, ih));
                    /**
                     * 得到指定像素（i,j)上的RGB值，
                     */
                    int pixel = bi.getRGB(i, j);
                    /**
                     * 分别进行位操作得到 r g b上的值
                     */
                    rgb[0] = (pixel & 0xff0000) >> 16;
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);
                    /**
                     * 进行换色操作，我这里是要把蓝底换成白底，那么就判断图片中rgb值是否在蓝色范围的像素
                     */
                    // if(rgb[0]<140&&rgb[0]>100 && rgb[1]<80&&rgb[1]>50 && rgb[2]<170&&rgb[2]>150 ){
//                    if (rgb[0] < 256 && rgb[0] > 230 && rgb[1] < 256 && rgb[1] > 230 && rgb[2] < 256 && rgb[2] > 230) {
//                        /**
//                         * 这里是判断通过，则把该像素换成白色
//                         */
//                        bi.setRGB(i, j, 0x000000);
//                    }
                    if (rgb[0] < 140 && rgb[0] > 100 && rgb[1] < 80 && rgb[1] > 50 && rgb[2] < 170 && rgb[2] > 150) {
                        /**
                         * 这里是判断通过，则把该像素换成白色
                         */
                        bi.setRGB(i, j, 0xffffff);
                    }

                }
            }
            System.out.println("\t处理完毕：" + files[x].getName());
            System.out.println();
            /**
             * 将缓冲对象保存到新文件中
             */
            FileOutputStream ops = new FileOutputStream(new File("d:/test/" + x + ".jpg"));
            ImageIO.write(bi, "jpg", ops);
            ops.flush();
            ops.close();
        }
    }

    public static void convert(String path, String outFile) throws IOException {

        //将背景色变透明

        try {

            BufferedImage image = ImageIO.read(new File(path));

            ImageIcon imageIcon = new ImageIcon(image);

            int w = imageIcon.getIconWidth();

            int h = imageIcon.getIconHeight();

            BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);

            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();

            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());

            int alpha = 0;

            for (int i = bufferedImage.getMinX(); i < w; i++) {

                for (int j = bufferedImage.getMinY(); j < h; j++) {

                    int rgb = bufferedImage.getRGB(i, j);

                    //以背景色左上角最小像素做参考系

                    int RGB = bufferedImage.getRGB(bufferedImage.getMinX(), bufferedImage.getMinY());

                    int r = (rgb & 0xff0000) >> 16;

                    int g = (rgb & 0xff00) >> 8;

                    int b = (rgb & 0xff);

                    int R = (RGB & 0xff0000) >> 16;

                    int G = (RGB & 0xff00) >> 8;

                    int B = (RGB & 0xff);

//a为色差范围值，渐变色边缘处理，数值需要具体测试，50左右的效果比较可以

                    int a = 45;

                    if (Math.abs(R - r) < a && Math.abs(G - g) < a && Math.abs(B - b) < a) {

                        alpha = 0;

                    } else {

                        alpha = 255;
                    }

                    rgb = (alpha << 24) | (rgb & 0x00ffffff);

                    bufferedImage.setRGB(i, j, rgb);

                }

            }

            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

            ImageIO.write(bufferedImage, "png", new File(outFile));

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public static void convert2(String imagePath, String outFile) throws IOException {

        //指定某种单色为透明

        try {

            BufferedImage image = ImageIO.read(new File(imagePath));

            ImageIcon imageIcon = new ImageIcon(image);

            int w = imageIcon.getIconWidth();

            int h = imageIcon.getIconHeight();

            BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);

            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();

            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());

            int alpha = 0;

            for (int i = bufferedImage.getMinX(); i < w; i++) {

                for (int j = bufferedImage.getMinY(); j < h; j++) {

                    int rgb = bufferedImage.getRGB(i, j);

                    //以白色为例

                    int RGB = Color.WHITE.getRGB();

                    int r = (rgb & 0xff0000) >> 16;

                    int g = (rgb & 0xff00) >> 8;

                    int b = (rgb & 0xff);

                    int R = (RGB & 0xff0000) >> 16;

                    int G = (RGB & 0xff00) >> 8;

                    int B = (RGB & 0xff);

                    if (Math.abs(R - r) < 15 && Math.abs(G - g) < 15 && Math.abs(B - b) < 15) {

                        alpha = 0;

                    } else {

                        alpha = 255;
                    }

                    rgb = (alpha << 24) | (rgb & 0x00ffffff);

                    bufferedImage.setRGB(i, j, rgb);

                }

            }

            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());

            ImageIO.write(bufferedImage, "png", new File(outFile));

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static void convert3(String imagePath, String outFile) throws IOException {

        //指定某种颜色替换成另一种

        BufferedImage image = null;

        image = ImageIO.read(new File(imagePath));

        int w = image.getWidth();

        int h = image.getHeight();

        int minx = image.getMinTileX();

        int miny = image.getMinTileY();

        for (int i = minx; i < w; i++) {

            for (int j = miny; j < h; j++) {

                int rgb = image.getRGB(i, j);

                //以黑色为例

                int RGB = Color.BLACK.getRGB();

                int r = (rgb & 0xff0000) >> 16;

                int g = (rgb & 0xff00) >> 8;

                int b = (rgb & 0xff);

                int R = (RGB & 0xff0000) >> 16;

                int G = (RGB & 0xff00) >> 8;

                int B = (RGB & 0xff);

                if (Math.abs(R - r) < 75 && Math.abs(G - g) < 75 && Math.abs(B - b) < 75) {

                    //0xff0000是红色的十六进制代码

                    image.setRGB(i, j, 0xff0000);

                }

            }

            ImageIO.write(image, "png", new File(outFile));

        }

    }

    @Test
    public  void test119() throws IOException {

        //原图路径：E:\\png格式logo\\H1.png

        try {

            convert("D:\\test\\0.jpg", "D:\\test\\1\\0.jpg");

        } catch (IOException e) {

            e.printStackTrace();

        }

        try {

            convert("D:\\test\\0.jpg", "D:\\test\\1\\2.jpg");

        } catch (IOException e) {

            e.printStackTrace();

        }

        try {

            convert("D:\\test\\0.jpg", "D:\\test\\1\\3.jpg");

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

}



