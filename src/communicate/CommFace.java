package communicate;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class CommFace {
    private String name;
    public void showCommFace(String name) {
        this.name = name;
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
        client(name);
        stage.show();

    }

    public void client(String name){
        try {
            Socket socket = new Socket("localhost",12345);
            new Thread(new Send(socket,name)).start();
            new Thread(new Receive(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
