package login;

import communicate.CommFace;
import communicate.CommFaceContorller;
import db.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import signup.AlterBox;
import signup.SignUpFace;

import java.sql.ResultSet;

/**
 * @author Prongs
 */
public class Controller {
    @FXML
    public javafx.scene.control.TextField loginUsernameField;
    @FXML
    public PasswordField loginPasswordField;

    /**
     * 点击注册按钮时弹出注册窗口
     */
    public void signUp() {
        SignUpFace signUpFace = new SignUpFace();
        signUpFace.showSignUpFace();
    }

    /**
     * 登陆时若用户名不存在提示用户是否已经注册或用户名是否写错
     * 若用户名正确密码错误则提示密码错误
     */
    public void login() throws Exception {
        DBUtil userNameDBUtil = new DBUtil();
        String userNameSql = "select * from users where username =" + "'" + loginUsernameField.getText() + "'";
        ResultSet usernameResult = userNameDBUtil.check(userNameSql);
        if (usernameResult.next()) {
            if (!(loginPasswordField.getText().equals(usernameResult.getString("PASSWORD")))) {
                AlterBox.display("登陆失败！", "密码错误！");
            } else {
                CommFace commFace = new CommFace();

                commFace.showCommFace(loginUsernameField.getText());
                LoginFace.getInstance().stage.close();
            }
        } else if (!(usernameResult.next())) {
            AlterBox.display("登陆失败！", "用户名错误或未注册！");
        }

    }
}
