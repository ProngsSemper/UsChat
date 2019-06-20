package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Prongs
 */
public class LoginFace extends Application {
    private static LoginFace instance;

    static LoginFace getInstance() {
        return instance;
    }

    public LoginFace() {
        instance = this;
    }

    /**
     * 为了让Controller用getInstance方法得到primaryStage
     * 在此创建一个stage来储存primaryStage
     */
    Stage stage = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //储存primaryStage
        stage = primaryStage;
        /*
          通过fxml文件来创建登陆主界面
         */
        Parent mainFace = FXMLLoader.load(getClass().getResource("MainFace.fxml"));
        Scene window = new Scene(mainFace, 600, 400);
        stage.setScene(window);
        stage.setTitle("欢迎来到聊天室");
        stage.show();
        stage.setOnCloseRequest(e -> System.exit(0));
    }
}
