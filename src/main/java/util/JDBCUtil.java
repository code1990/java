package util;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: spider
 * @Date: 2020-08-02 13:31
 * @Author: code1990
 * @Description:
 */
public class JDBCUtil {

    private static final String connectionURL =
            "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "123456";

    //创建数据库的连接
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(connectionURL, username, password);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    //关闭数据库的连接
    public static void close(ResultSet rs, Statement stmt, Connection con) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }

    public static void insertList(List<String> list) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            Statement st = con.createStatement();
            for (int i = 0; i < list.size(); i++) {
                st.addBatch(list.get(i));
            }
            st.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                close(rs, stmt, con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getResult(String sql) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            //建立连接
            con = getConnection();
            StringBuilder sb1 = new StringBuilder();

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            //获取列数
            ResultSetMetaData md = rs.getMetaData();
            int columnSize = md.getColumnCount();

            System.out.println("查询结果如下:");
            //打印字段名
            StringBuilder sb2= new StringBuilder();
            for (int i = 1; i <= columnSize; i++) {
                System.out.printf("%-12s", md.getColumnName(i));
//                sb2.append(md.getColumnName(i)+"\t");
            }
//            sb1.append(sb2.toString()+"\n");
            while (rs.next()) {
                StringBuilder sb3= new StringBuilder();
                for (int i = 1; i <= columnSize; i++) {
                    System.out.printf("%-12s", rs.getObject(i));
                    sb3.append(rs.getObject(i)+"\t");
                }
                sb1.append(sb3.toString()+"\n");
            }
            TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\1233.txt", sb1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                close(rs, stmt, con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static List<String[]> getResultList(String sql) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String[]> list = new ArrayList<>();
        try {
            //建立连接
            con = getConnection();
            StringBuilder sb1 = new StringBuilder();

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            //获取列数
            ResultSetMetaData md = rs.getMetaData();
            int columnSize = md.getColumnCount();

            System.out.println("查询结果如下:");
            //打印字段名
            StringBuilder sb2= new StringBuilder();
            for (int i = 1; i <= columnSize; i++) {
                System.out.printf("%-12s", md.getColumnName(i));
//                sb2.append(md.getColumnName(i)+"\t");
            }
//            sb1.append(sb2.toString()+"\n");

            while (rs.next()) {
                StringBuilder sb3= new StringBuilder();
                String[] array = new String[columnSize];
                for (int i = 1; i <= columnSize; i++) {
//                    System.out.printf("%-12s", rs.getObject(i));
                    sb3.append(rs.getObject(i)+"\t");
                    String str="";
                    if(rs.getObject(i)==null){
                        str="";
                    }else{
                        str =rs.getObject(i).toString();
                    }
                    array[i-1]=str;
                }
                list.add(array);
                sb1.append(sb3.toString()+"\n");
            }

            TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\1233.txt", sb1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                close(rs, stmt, con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Test
    public void getInfo(){
        String sql = "select * from t_fund_cc_mx;";
        List<String[]> list = getResultList(sql);
        sql = "select * from t_com_hy_gn_mx;";
        List<String[]> list2 = getResultList(sql);
//        for (int i = 0; i < list.size(); i++) {
//            String ccName = list.get(i)[2];
////            System.out.println(ccName);
//            int count =0;
//            String[] array = "立讯精密、歌尔股份、卓胜微、信维通信、兆易创新、圣邦股份、三安光电、京东方A、TCL科技".split("、");
//            for (int j = 0; j <array.length ; j++) {
//                if(ccName.contains(array[j])){
//                    count+=1;
//                }
//            }
//            System.out.println(list.get(i)[1]+"\t"+count);
//        }
        for (int i = 0; i < list2.size(); i++) {
            System.out.println(Arrays.toString(list2.get(i)));
        }
    }
}
