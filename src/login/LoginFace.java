package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginFace extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /**
         * 通过fxml文件来创建登陆主界面
         */
        Parent mainFace = FXMLLoader.load(getClass().getResource("MainFace.fxml"));
        Scene window = new Scene(mainFace, 600, 400);
        primaryStage.setScene(window);
        primaryStage.setTitle("欢迎来到聊天室");
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.exit(0));
    }
}
