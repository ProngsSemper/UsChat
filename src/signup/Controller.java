package signup;

import dao.UsersDao;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Users;

import java.sql.SQLException;

public class Controller {
    public Button Cancel;
    public TextField usernameField;
    public TextField passwordField;
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
     */
    public void signup() throws SQLException {
        if (passwordField.getText().equals(passwordAgainField.getText())) {
            UsersDao newUser = new UsersDao();
            Users users = new Users();
            users.setUsername(usernameField.getText());
            users.setPassword(passwordField.getText());
            newUser.addUser(users);
        }else{
            /**
             * 当用户两次输入的密码不相同时弹出警告框
             */
            AlterBox.display("警告！","两次输入的密码不相同！");
        }
    }
}
