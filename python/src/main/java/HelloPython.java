import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Properties;

/**
 * @author issuser
 * @date 2019-09-06 21:12
 * 使用jpython在Java代码中调用Python代码
 * <p>
 * 混合编程的目的只是为了一个 实现知识点的相互理解
 */
public class HelloPython {

    public static void main(String[] args) throws Exception {

        /*必须初始化环境否则直接报错*/
        initJavaPythonEnvironment();
        /*使用本地的方式直接调用Python代码*/
        invokePythonMethodDirect();
        /*使用文件的方式调用*/
        invokePythonMethodFile();
        /*使用命令行窗口方式调用 推荐*/
        invokePythonMethodByRuntime();
        /*传递参数*/
        invokePythonMethodByRuntimeParam(1,2);
    }

    public static void initJavaPythonEnvironment() {
        Properties props = new Properties();
        props.put("python.home", "path to the Lib folder");
        // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
        props.put("python.console.encoding", "UTF-8");
        //don't respect java accessibility, so that we can access protected members on subclasses
        props.put("python.security.respectJavaAccessibility", "false");
        props.put("python.import.site", "false");

        Properties preprops = System.getProperties();

        PythonInterpreter.initialize(preprops, props, new String[0]);
    }

    /*本方式抛出异常*/
    public static void invokePythonMethodDirect() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("a=[1,6,0,4]");
        interpreter.exec("print(sorted(a))");
        interpreter.exec("print sorted(a)");
    }

    public static void invokePythonMethodFile() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("E:\\tmp\\tmp\\bootPython\\src\\main\\python\\add.py");
        PyFunction pyFunction = interpreter.get("add", PyFunction.class);
        int a = 5;
        int b = 6;
        PyObject pyObject = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println(pyObject);
    }

    public static void invokePythonMethodByRuntime() {
        Process process;
        try {
            process = Runtime.getRuntime().exec("python E:\\tmp\\tmp\\bootPython\\src\\main\\python\\num.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void invokePythonMethodByRuntimeParam(int a,int b) {
        Process process;
        try {
            String[] args = new String[]{"python","E:\\tmp\\tmp\\bootPython\\src\\main\\python\\sys.py",
            String.valueOf(a),String.valueOf(b)};
            process = Runtime.getRuntime().exec(args);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
