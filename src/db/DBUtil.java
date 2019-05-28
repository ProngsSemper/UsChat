package db;

import java.sql.*;

public class DBUtil {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/jdbcdemo";
    private static final String name = "root";
    private static final String password = "huxi913836";
    private static Connection connection = null;
    public PreparedStatement preparedStatement;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, name, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public ResultSet check(String sql) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

}
