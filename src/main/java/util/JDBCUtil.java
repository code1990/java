package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: spider
 * @Date: 2020-08-02 13:31
 * @Author: code1990
 * @Description:
 */
public class JDBCUtil {

    static ConnectionPool pool = new ConnectionPool();

    private static  String connectionURL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=UTC";
    private static  String username = "root";
    private static  String password = "123456";

    //创建数据库的连接
    public static Connection getConnection() {
        Connection connection=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionURL, username, password);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection(String db) {
        try {
            connectionURL =connectionURL.replace("test",db);
            System.out.println(connectionURL);
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(connectionURL, username, password);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    public static void executeSql(Connection connection, String sql) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    public static void executeSql(Connection connection, List<String> list) {
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            for (int i = 0; i < list.size(); i++) {
                stmt.executeUpdate(list.get(i));
            }
            stmt.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
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

    public static void insertList(ConnectionPool pool,List<String> list)  {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection connection =null;
        try {
            connection=pool.getConnection();
            Statement st = pool.getConnection().createStatement();
            for (int i = 0; i < list.size(); i++) {
                st.addBatch(list.get(i));
            }
            st.executeBatch();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.close(connection);
    }

    public static void insertList(Connection connection,List<String> list)  {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Statement st = connection.createStatement();
            for (int i = 0; i < list.size(); i++) {
                st.addBatch(list.get(i));
            }
            st.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: updateTest
     * @Description: PreparedStatement批量更新
     * @return void    返回类型
     */
    public static void update(ConnectionPool pool,List<String> list) {
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

    public static void update(ConnectionPool pool,String sql) {
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

    public static void getResult(ConnectionPool pool,String sql) {
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

    public static List<String> getList(Connection con,String sql) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> list = new ArrayList<>();
        try {
            //建立连接
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
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        pool.close(con);
        return list;
    }

    public static List<String[]> getResultList(Connection con,String sql) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String[]> list = new ArrayList<>();
        try {
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
//        pool.close(con);
        return list;
    }

    public static List<String[]> getResultList(ConnectionPool pool,String sql) {
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

    public static List<String> getList(ConnectionPool pool,String sql) {
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
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.close(con);
        return list;
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

    public static void insertList(List<String> list)  {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection connection =null;
        try {
            connection=pool.getConnection();
            Statement st = pool.getConnection().createStatement();
            for (int i = 0; i < list.size(); i++) {
                st.addBatch(list.get(i));
            }
            st.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pool.close(connection);
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

}
