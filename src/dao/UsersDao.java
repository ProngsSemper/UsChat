package dao;

import db.DBUtil;
import model.LoginUsers;
import model.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsersDao {
    public void addUser(Users users) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = "" +
                "insert into users" +
                "(username,PASSWORD)" +
                "values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, users.getUsername());
        preparedStatement.setString(2, users.getPassword());
        preparedStatement.execute();
    }

    public void addLoginUser(LoginUsers loginUsers) throws SQLException {
        Connection connection = DBUtil.getConnection();
        String sql = "" +
                "insert into loginusers" +
                "(loginUsers)" +
                "values(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, loginUsers.getLoginUsers());
        preparedStatement.execute();
    }
}
