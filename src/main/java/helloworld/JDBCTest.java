package helloworld;

import java.sql.*;

/**
 * @author issuser
 * @date 2019-08-21 22:51
 */
public class JDBCTest {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("mysql驱动加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useSSL=false", "root", "123456");
            System.out.println("mysql连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        /*更新executeUpdate 查询executeQuery
        * 添加批处理 addBatch
        * 执行批处理executeBath
        * */
        ResultSet rs = statement.executeQuery("select * from t_test");
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            System.out.println(id + "\t" + name);
        }
        PreparedStatement ps = connection.prepareStatement("select * from t_test where id=?");
        ps.setInt(1, 1);
        rs = ps.executeQuery();
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            System.out.println(id + "\t" + name);
        }
    }
}
