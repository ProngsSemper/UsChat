package signup;

import dao.UsersDao;
import db.DBUtil;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Users;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
    public Button Cancel;
    public TextField signUpUsernameField;
    public TextField signUpPasswordField;
    public TextField passwordAgainField;

    /**
     * 点击取消时可关闭窗口
     */
    public void exit() {
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * 实现注册功能，将用户名与用户密码添加到数据库
     * 当用户注册时没填写密码则提醒用户填写密码
     * 若用户名已存在数据库则提示用户此用户名已被注册
     * 当用户两次输入的密码不相同时弹出警告框
     */
    public void signup() throws SQLException {
        while (true) {
            if (signUpPasswordField.getText().equals("")) {
                AlterBox.display("错误！", "请设置密码！");
                break;
            } else if (signUpPasswordField.getText().equals(passwordAgainField.getText())) {
                String sql = "select * from users where username =" + "'" + signUpUsernameField.getText() + "'";
                DBUtil dbUtil = new DBUtil();
                ResultSet result = dbUtil.check(sql);
                UsersDao newUser = new UsersDao();
                Users users = new Users();
                if (result.next()) {
                    AlterBox.display("错误！", "该用户名已被注册！");
                    break;
                }
                users.setUsername(signUpUsernameField.getText());
                users.setPassword(signUpPasswordField.getText());
                newUser.addUser(users);
                AlterBox.display("成功！", "注册成功！");
                //当注册成功时关闭注册窗口
                exit();
                break;
            } else {
                AlterBox.display("警告！", "两次输入的密码不相同！");
                break;
            }
        }
    }
}
