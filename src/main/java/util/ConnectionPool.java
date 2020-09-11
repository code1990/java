package util;


import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * @author 911
 * @date 2020-08-25 16:13
 */
public class ConnectionPool implements DataSource {
    // 链表 --- 实现 栈结构 、队列 结构
    private LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectionPool() {
        // 一次性创建10个连接
        for (int i = 0; i < 10; i++) {
            try {
                Connection conn = JDBCUtil.getConnection();
                // 将连接加入连接池中
                pool.add(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        // 取出连接池中一个连接
        final Connection conn = pool.removeFirst(); // 删除第一个连接返回
//        System.out.println("取出一个连接剩余 " + pool.size() + "个连接！");
        // 将目标Connection对象进行增强
        Connection connProxy = (Connection) Proxy.newProxyInstance(conn
                        .getClass().getClassLoader(), conn.getClass().getInterfaces(),
                new InvocationHandler() {
                    // 执行代理对象任何方法 都将执行 invoke
                    @Override
                    public Object invoke(Object proxy, Method method,
                                         Object[] args) throws Throwable {
                        if (method.getName().equals("close")) {
                            // 需要加强的方法
                            // 不将连接真正关闭，将连接放回连接池
                            releaseConnection(conn);
                            return null;
                        } else {
                            // 不需要加强的方法
                            return method.invoke(conn, args); // 调用真实对象方法
                        }
                    }
                });
        return connProxy;
    }

    // 将连接放回连接池
    public void releaseConnection(Connection conn) {
        pool.add(conn);
//        System.out.println("将连接 放回到连接池中 数量:" + pool.size());
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    public void close(Connection connection){
        pool.add(connection);
    }
}
