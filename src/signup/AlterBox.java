package signup;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 创建提示
 */
public class AlterBox {

    public static void display(String title, String message) {
        Stage window = new Stage();

        /**
         * 让用户不能切换到其他窗口进行操作
         */
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(500);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("关闭");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        /*
        展示窗口并等待其关闭
         */
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}