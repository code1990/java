package python;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import util.JDBCUtil;
import util.SQLiteUtil;
import util.TxtUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author 911
 * @date 2020-11-20 10:21
 */
public class TushareTest {

//    public static void main(String[] args) throws Exception {
//
//        /*必须初始化环境否则直接报错*/
//        initJavaPythonEnvironment();
//        /*使用本地的方式直接调用Python代码*/
//        invokePythonMethodDirect();
//        /*使用文件的方式调用*/
////        invokePythonMethodFile();
//        /*使用命令行窗口方式调用 推荐*/
//        invokePythonMethodByRuntime();
//        /*传递参数*/
//        invokePythonMethodByRuntimeParam(1, 2);
//    }

    public static void initJavaPythonEnvironment() {
        Properties props = new Properties();
        props.put("python.home", "");
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
        interpreter.exec("import tushare as ts");
        interpreter.exec("pro = ts.pro_api('4907b8834a0cecb6af0613e29bf71847206c41ddc3e598b9a25a0203')");
        interpreter.exec("print(ts.__version__)");
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
            process = Runtime.getRuntime().exec("D:\\sdk\\Python36\\python.exe D:\\gitee\\client\\tushare\\api\\ts01.py");
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

    public static void invokePythonMethodByRuntimeParam(int a, int b) {
        Process process;
        try {
            String[] args = new String[]{"python", "E:\\tmp\\tmp\\bootPython\\src\\main\\python\\sys.py",
                    String.valueOf(a), String.valueOf(b)};
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


//    @Test
//    public void getInfo() {
//
//        Document document = HttpUtil.getHtml("https://tushare.pro/document/2");
//        Elements elements = document.getElementsByClass("document").get(0).getElementsByTag("a");
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < elements.size(); i++) {
//            Element a = elements.get(i);
//            String url = a.attr("href");
//            if (!url.startsWith("http")) {
//                url = "https://tushare.pro" + url;
//            }
//            System.out.println(a.text() + "\t" + url);
//            sb.append(a.text() + "\t" + url + "\n");
//        }
//        TxtUtil.writeTxt(new File("tushare.txt"), sb.toString());
//    }
//
//    @Test
//    public void getInfo111() {
//        List<String> list = TxtUtil.readTxt(new File("tushare.txt"));
//        StringBuilder sb1 = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            String title = list.get(i).split("\t")[0];
//            String url = list.get(i).split("\t")[1];
//            Document document = HttpUtil.getHtml(url);
//            Element element = document.getElementsByClass("content col-md-9 col-sm-8 col-xs-12").get(0);
//
//            if (element.text().contains("接口：")) {
//                System.out.println(title);
//                String tableName = element.getElementsByTag("p").get(0).text().replace("接口：", "").split(" ")[0];
//                int length = 1;
//                if(element.getElementsByTag("table").size()<2){
//                    length=0;
//                }
//                Elements elements = element.getElementsByTag("table").get(length).getElementsByTag("tr");
//                StringBuilder sb = new StringBuilder();
//                sb.append("CREATE TABLE  " + tableName + " (\n");
//                sb.append("  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',\n");
//                for (int j = 1; j < elements.size(); j++) {
//                    String field = elements.get(j).text().split(" ")[0];
//                    sb.append("  `" + field + "` varchar(200) DEFAULT NULL,\n");
//                    System.out.println(elements.get(j).text());
//                }
//                sb.append("PRIMARY KEY (`id`)\n");
//                sb.append(") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;\n");
//                sb1.append(sb.toString()+"\n");
//            }
////            System.out.println(text.replaceAll(" ","\n"));
//        }
//        TxtUtil.writeTxt(new File("ts_mysql_.sql"),sb1.toString());
//    }

//    @Test
//    public void getSqlite3(){
//        List<String> list = TxtUtil.readTxt(new File("tushare.txt"));
//        StringBuilder sb1 = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            String title = list.get(i).split("\t")[0];
//            String url = list.get(i).split("\t")[1];
//            Document document = HttpUtil.getHtml(url);
//            Element element = document.getElementsByClass("content col-md-9 col-sm-8 col-xs-12").get(0);
//
//            if (element.text().contains("接口：")) {
//                System.out.println(title);
//                String tableName = element.getElementsByTag("p").get(0).text().replace("接口：", "").split(" ")[0];
//                int length = 1;
//                if(element.getElementsByTag("table").size()<2){
//                    length=0;
//                }
//                Elements elements = element.getElementsByTag("table").get(length).getElementsByTag("tr");
//                StringBuilder sb = new StringBuilder();
//                sb.append("CREATE TABLE  " + tableName + " (\n");
//                sb.append("  `id` bigint(20) PRIMARY KEY,\n");
//                for (int j = 1; j < elements.size(); j++) {
//                    String field = elements.get(j).text().split(" ")[0];
//
//                    if(j==elements.size()-1){
//                        sb.append("  `" + field + "` varchar(200) DEFAULT NULL\n");
//                    }else{
//                        sb.append("  `" + field + "` varchar(200) DEFAULT NULL,\n");
//                    }
//                    System.out.println(elements.get(j).text());
//                }
//                sb.append(");\n");
//                sb1.append(sb.toString()+"\n");
//            }
////            System.out.println(text.replaceAll(" ","\n"));
//        }
//        TxtUtil.writeTxt(new File("ts_sqlite3_.sql"),sb1.toString());
//
//    }

    @Test
    public void getInfo1111(){
//        String path1 = "C:\\Users\\xiala\\Desktop\\index_basic\\SZSE.json";
//        String path2 ="C:\\Users\\xiala\\Desktop\\index_basic.json";
//        TxtUtil.copyFile(path1,path2);
        String str ="C:\\Users\\xiala\\Desktop\\index_basic.json";
        List<String> list = TxtUtil.readTxt(new File(str));
        JSONObject jsonObject = JSONObject.parseObject(list.get(0));
        JSONArray array = jsonObject.getJSONObject("data").getJSONArray("fields");
        JSONArray array2 = jsonObject.getJSONObject("data").getJSONArray("items");
        System.out.println(array2.get(0).toString());
        String tableName = new File(str).getName().replace(".json","");
//        for (int i = 0; i <array.size() ; i++) {
//            System.out.println(array.get(i).toString());
//        }
//        createTable(array,tableName);
        insertTable(array,tableName,array2);
    }

    public static void createTable(JSONArray array,String tableName){
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb1.append("CREATE TABLE  " + tableName + " (\n");
        sb1.append("  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',\n");
        for (int j = 0; j < array.size(); j++) {
            String field = array.getString(j);
            sb1.append("  `" + field + "` varchar(200) DEFAULT NULL,\n");
        }
        sb1.append("\tPRIMARY KEY (`id`)\n");
        sb1.append(") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;\n");

        sb2.append("CREATE TABLE  " + tableName + " (\n");
        sb2.append("  `id` bigint(20) PRIMARY KEY,\n");
        for (int j = 0; j < array.size(); j++) {
            String field = array.getString(j);
            if(j==array.size()-1){
                sb2.append("  `" + field + "` varchar(200) DEFAULT NULL\n");
            }else{
                sb2.append("  `" + field + "` varchar(200) DEFAULT NULL,\n");
            }
        }
        sb2.append(");\n");

        System.out.println(sb1.toString());
        System.out.println(sb2.toString());

        Connection connection1 = JDBCUtil.getConnection("stock");
        JDBCUtil.executeSql(connection1,sb1.toString());

        String path ="D:\\github\\java\\stock.db";
        Connection connection2 = SQLiteUtil.getConnection(path);
        SQLiteUtil.executeSql(connection2,sb2.toString());
    }

    public void insertTable(JSONArray array,String tableName,JSONArray array2){
        StringBuilder sb1 = new StringBuilder("INSERT INTO "+tableName+" (");
        for (int j = 0; j < array.size(); j++) {
            String field = array.getString(j);
            if(j==array.size()-1){
                sb1.append(""+field+"");
            }else {
                sb1.append(""+field+",");
            }
        }
        sb1.append(") VALUES (");
        List<String> list = new ArrayList<>();
        for (int j = 0; j < array2.size(); j++) {
            JSONArray jsonArray = array2.getJSONArray(j);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb1.toString());
            sb3.append(getChild(jsonArray));
            list.add(sb3.toString());
        }
        Connection connection1 = JDBCUtil.getConnection("stock");
        String path ="D:\\github\\java\\stock.db";

        Connection connection2 = SQLiteUtil.getConnection(path);
        SQLiteUtil.executeSql(connection2,list);
        JDBCUtil.executeSql(connection1,list);
    }

    public String getChild(JSONArray jsonArray){
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i <jsonArray.size() ; i++) {
            String value = jsonArray.getString(i);
            if(i==jsonArray.size()-1){
                sb2.append("'"+value+"'");
            }else {
                sb2.append("'"+value+"',");
            }
        }
        sb2.append(");"+"\n");
        return sb2.toString();
    }
}
