package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class TestClass {
    private static WebDriver driver;

    public static void initFirefox() {

        //  firefox.exe同样要与当前使用的火狐浏览器版本一一对应，下载的地址可在淘宝或者GitHub

        //  指定火狐浏览器程序的位置
        System.setProperty("webdriver.firefox.bin", "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");

        //  指定firefox.exe插件的位置
        System.setProperty("webdriver.gecko.driver", "D:\\github\\java\\src\\main\\resources\\geckodriver.exe");

        driver = new FirefoxDriver();
    }
    public static void initChromeDriver(){
        //  chromedriver.exe要与当前使用的谷歌浏览器版本一一对应，下载的地址可在淘宝或者GitHub，并将其解压放在与谷歌
        //  .exe 文件同级下
        File file = new File("D:\\github\\java\\src\\main\\resources\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

        driver  = new ChromeDriver();
    }
    public static void main(String[] args) {
        //  初始化火狐浏览器加载所需的配置程序
        initChromeDriver();

        //  在打开地址前，清除cookies
        driver.manage().deleteAllCookies();

        //  同步浏览器
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //  打开目标地址，这个使用百度为例。只要是web系统都可以
        driver.get("https://www.baidu.com");

        System.out.println(driver.getTitle());
    }
}
