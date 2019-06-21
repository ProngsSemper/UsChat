package db;

import java.sql.*;

/**
 * @author Prongs
 */
public class DBUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/jdbcdemo";
    private static final String NAME = "root";
    private static final String PASSWORD = "huxi913836";
    private static Connection connection = null;
    private PreparedStatement preparedStatement;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    /**
     * 检查数据库中是否有某个数据
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public ResultSet check(String sql) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

}
