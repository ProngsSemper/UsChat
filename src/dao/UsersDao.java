package dao;

import db.DBUtil;
import model.LoginUsers;
import model.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public List<Users> query() throws Exception {
        Connection connection = DBUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select username,PASSWORD from users");
        List<Users> usersList = new ArrayList<>();
        Users u = null;
        while (result.next()) {
            u = new Users();
            Scanner usernameScanner = new Scanner(System.in);
            Scanner passwordScanner = new Scanner(System.in);
            u.setUsername(usernameScanner.nextLine());
            u.setPassword(passwordScanner.nextLine());
            usersList.add(u);
        }
        return usersList;
    }

    public List<Users> query(String username) throws Exception {
        List<Users> usersList = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select * from users ");
        stringBuilder.append("where username = ?");
        PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        Users users = null;
        while (resultSet.next()) {
            users = new Users();
            users.setUsername(resultSet.getString("username"));
            users.setPassword(resultSet.getString("PASSWORD"));
            usersList.add(users);
        }
        return usersList;
    }

    public Users get() {
        return null;
    }
}
