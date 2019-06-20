package communicate;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author Prongs
 */
public class ServerFace extends Application {
//    private Server server = new Server();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /**
         * 启动服务端线程
         */
//        new Thread(server).start();
        /**
         * 通过fxml文件来创建注册界面
         */
        try {
            URL location = getClass().getResource("ServerFace.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load();
            Server server = fxmlLoader.getController();
            new Thread(server).start();
//            Parent root = FXMLLoader.load(getClass().getResource("ServerFace.fxml"));
            Scene window = new Scene(root, 600, 400);
            primaryStage.setScene(window);
            primaryStage.setTitle("服务器");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.setOnCloseRequest(e -> System.exit(0));
    }
}
