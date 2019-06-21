package communicate;

import javafx.application.Application;
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
//    private server server = new server();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /*
         通过fxml文件来创建注册界面
         */
        try {
            URL location = getClass().getResource("ServerFace.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            //如果使用 Parent root = FXMLLoader.load(...) 静态读取方法，无法获取到Controller的实例对象
            Parent root = fxmlLoader.load();
            Server server = fxmlLoader.getController();
            /*
            启动服务端线程
             */
            new Thread(server).start();
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
