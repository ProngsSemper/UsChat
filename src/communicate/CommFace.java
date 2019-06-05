package communicate;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CommFace {
    public void showCommFace() {
        Stage stage = new Stage();
        /**
         * 通过fxml文件来创建注册界面
         */
        try {
            Parent signFace = FXMLLoader.load(getClass().getResource("CommFace.fxml"));
            Scene window = new Scene(signFace, 600, 400);
            stage.setScene(window);
            stage.setTitle("聊天室");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
