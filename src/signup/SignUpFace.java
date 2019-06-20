package signup;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author Prongs
 */
public class SignUpFace {
    public void showSignUpFace() {
        Stage stage = new Stage();
        /*
          通过fxml文件来创建注册界面
         */
        try {
            Parent signFace = FXMLLoader.load(getClass().getResource("SignUpFace.fxml"));
            Scene window = new Scene(signFace, 600, 400);
            stage.setScene(window);
            stage.setTitle("欢迎注册");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
