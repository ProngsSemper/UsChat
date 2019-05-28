package main;

import db.DBUtil;
import javafx.scene.control.TextField;
import signup.AlterBox;
import signup.SignUpFace;

import java.sql.ResultSet;

public class Controller {
    public javafx.scene.control.TextField loginUsernameField;
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
                /**
                 * 登陆成功后要实现的功能
                 */
            }
        } else if (!(usernameResult.next())) {
            AlterBox.display("登陆失败！", "用户名错误或未注册！");
        }

    }
}
