package util;

import com.itranswarp.compiler.JavaStringCompiler;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author 911
 * @date 2020-12-22 16:44
 */
public class ClassUtil {

    public static void main(String[] args) throws Exception {
        // 传入String类型的代码
        String source = "import java.util.Arrays;public class Main" +
                "{" +
                "public static void main(String[] args) {" +
                "System.out.println(Arrays.toString(args));" +
                "}" +
                "}";
        StringCompiler.run(source, "1", "2");
    }
}

class StringCompiler {
    public static Object run(String source, String...args) throws Exception {
        // 声明类名
        String className = "Main";
        String packageName = "util";
        // 声明包名：package top.fomeiherz;
        String prefix = String.format("package %s;", packageName);
        // 全类名：top.fomeiherz.Main
        String fullName = String.format("%s.%s", packageName, className);

        // 编译器
        JavaStringCompiler compiler = new JavaStringCompiler();
        // 编译：compiler.compile("Main.java", source)
        Map<String, byte[]> results = compiler.compile(className + ".java", prefix + source);
        // 加载内存中byte到Class<?>对象
        Class<?> clazz = compiler.loadClass(fullName, results);
        // 创建实例
        Object instance = clazz.newInstance();
        Method mainMethod = clazz.getMethod("main", String[].class);
        // String[]数组时必须使用Object[]封装
        // 否则会报错：java.lang.IllegalArgumentException: wrong number of arguments
        return mainMethod.invoke(instance, new Object[]{args});
    }
}