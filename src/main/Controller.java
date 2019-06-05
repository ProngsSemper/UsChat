package main;

import communicate.Client;
import communicate.CommFace;
import communicate.Server;
import dao.UsersDao;
import db.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.LoginUsers;
import model.Users;
import signup.AlterBox;
import signup.SignUpFace;

import java.io.IOException;
import java.sql.ResultSet;

public class Controller {
    @FXML
    public javafx.scene.control.TextField loginUsernameField;
    @FXML
    public TextField loginPasswordField;

    /**
     * 点击注册按钮时弹出注册窗口
     */
    public void SignUp() {
        SignUpFace signUpFace = new SignUpFace();
        signUpFace.showSignUpFace();
    }

    /**
     * 登陆时若用户名不存在提示用户是否已经注册或用户名是否写错
     *若用户名正确密码错误则提示密码错误
     */
    public void Login() throws Exception {
        DBUtil userNameDBUtil = new DBUtil();
        DBUtil passwordNameDBUtil = new DBUtil();
        String userNameSql = "select * from users where username =" + "'" + loginUsernameField.getText() + "'";
        String passwordSql = "select * from users where PASSWORD =" + "'" + loginPasswordField.getText() + "'";
        ResultSet usernameResult = userNameDBUtil.check(userNameSql);
        ResultSet passwordResult = passwordNameDBUtil.check(passwordSql);
        if (usernameResult.next()) {
            if (!(passwordResult.next())) {
                AlterBox.display("登陆失败！", "密码错误！");
            } else {
                UsersDao loginUser = new UsersDao();
                LoginUsers loginUsers = new LoginUsers();
                loginUsers.setLoginUsers(loginUsernameField.getText());
                loginUser.addLoginUser(loginUsers);
                UserName.name = loginUsernameField.getText();
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            Server server = new Server();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                Thread.sleep(1000);
                Client client = new Client();
                CommFace chat = new CommFace();
                chat.showCommFace();
            }
        } else if (!(usernameResult.next())) {
            AlterBox.display("登陆失败！", "用户名错误或未注册！");
        }

    }
}
