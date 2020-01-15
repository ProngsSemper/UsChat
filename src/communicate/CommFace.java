package communicate;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;

/**
 * @author Prongs
 */
public class CommFace {
    private String name;

    public void showCommFace(String name) {
        this.name = name;
        Stage stage = new Stage();
        /*
          通过fxml文件来创建注册界面
         */
        try {
            URL location = getClass().getResource("CommFace.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load();
            CommFaceController contorller = fxmlLoader.getController();
            Scene window = new Scene(root, 600, 400);
            stage.setScene(window);
            stage.setTitle("聊天室");
            stage.show();

            //使用回车键即可发送消息
            contorller.chatArea.addEventFilter(KeyEvent.KEY_PRESSED, Key -> {
                if (Key.getCode().equals(KeyCode.ENTER)) {
                    contorller.sendMsg();
                    Key.consume();
                }
            });

            stage.setOnCloseRequest(e -> System.exit(0));

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
          开启客户端并将用户名传给客户端
         */
        client(name);
    }

    /**
     * 客户端方法
     */
    private void client(String name) {
        try {
            Socket socket = new Socket("localhost", 12345);
            new Thread(new Send(socket, name)).start();
            new Thread(new Receive(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
