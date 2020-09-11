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

    static ConnectionPool pool = new ConnectionPool();

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

    public static void insertList(List<String> list)  {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            Statement st = con.createStatement();
            for (int i = 0; i < list.size(); i++) {
                st.addBatch(list.get(i));
            }
            st.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.close(con);
    }

    /**
     * @Title: updateTest
     * @Description: PreparedStatement批量更新
     * @return void    返回类型
     */
    public static void update(List<String> list) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            con.setAutoCommit(false);
            for (int i = 0; i < list.size(); i++) {
                // 每200条执行一次，避免内存不够的情况
                stmt.addBatch(list.get(i));
                if (i > 0 && i % 200 == 0) {
                    stmt.executeBatch();
                }
            }
            // 最后执行剩余不足200条的
            stmt.executeBatch();
            // 执行完后，手动提交事务
            con.commit();
            // 在把自动提交打开
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.close(con);
    }

    public static void update(String sql) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            stmt = con.createStatement();
            con.setAutoCommit(false);
            stmt.addBatch(sql);
            // 最后执行剩余不足200条的
            stmt.executeBatch();
            // 执行完后，手动提交事务
            con.commit();
            // 在把自动提交打开
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.close(con);
    }

    public static void getResult(String sql) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            //建立连接
            con = pool.getConnection();
            StringBuilder sb1 = new StringBuilder();

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            //获取列数
            ResultSetMetaData md = rs.getMetaData();
            int columnSize = md.getColumnCount();

//            System.out.println("查询结果如下:");
//            //打印字段名
//            StringBuilder sb2= new StringBuilder();
//            for (int i = 1; i <= columnSize; i++) {
//                System.out.printf("%-12s", md.getColumnName(i));
////                sb2.append(md.getColumnName(i)+"\t");
//            }
//            sb1.append(sb2.toString()+"\n");
            while (rs.next()) {
                StringBuilder sb3= new StringBuilder();
                for (int i = 1; i <= columnSize; i++) {
//                    System.out.printf("%-12s", rs.getObject(i));
                    sb3.append(rs.getObject(i)+"\t");
                }
                sb1.append(sb3.toString()+"\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.close(con);
    }

    public static List<String[]> getResultList(String sql) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String[]> list = new ArrayList<>();
        try {
            //建立连接
            con = pool.getConnection();
            StringBuilder sb1 = new StringBuilder();

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            //获取列数
            ResultSetMetaData md = rs.getMetaData();
            int columnSize = md.getColumnCount();

//            System.out.println("查询结果如下:");
            //打印字段名
//            StringBuilder sb2= new StringBuilder();
//            for (int i = 1; i <= columnSize; i++) {
////                System.out.printf("%-12s", md.getColumnName(i));
////                sb2.append(md.getColumnName(i)+"\t");
//            }
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.close(con);
        return list;
    }

    public static List<String> getList(String sql) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> list = new ArrayList<>();
        try {
            //建立连接
            con = pool.getConnection();
            StringBuilder sb1 = new StringBuilder();

            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            //获取列数
            ResultSetMetaData md = rs.getMetaData();
            int columnSize = md.getColumnCount();
            while (rs.next()) {
                if(rs.getObject(1)==null){
                    return list;
                }
                list.add(rs.getObject(1).toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.close(con);
        return list;
    }

}
