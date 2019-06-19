package login;

import communicate.CommFace;
import dao.UsersDao;
import db.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import model.LoginUsers;
import signup.AlterBox;
import signup.SignUpFace;

import java.sql.ResultSet;

public class Controller {
    @FXML
    public javafx.scene.control.TextField loginUsernameField;
    @FXML
    public PasswordField loginPasswordField;

    /**
     * 点击注册按钮时弹出注册窗口
     */
    public void SignUp() {
        SignUpFace signUpFace = new SignUpFace();
        signUpFace.showSignUpFace();
    }

    /**
     * 登陆时若用户名不存在提示用户是否已经注册或用户名是否写错
     * 若用户名正确密码错误则提示密码错误
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
                 * 将登陆用户写进数据库
                 */
                UsersDao loginUser = new UsersDao();
                LoginUsers loginUsers = new LoginUsers();
                loginUsers.setLoginUsers(loginUsernameField.getText());
                loginUser.addLoginUser(loginUsers);
                CommFace commFace = new CommFace();
                commFace.showCommFace(loginUsernameField.getText());
            }
        } else if (!(usernameResult.next())) {
            AlterBox.display("登陆失败！", "用户名错误或未注册！");
        }

    }
}
