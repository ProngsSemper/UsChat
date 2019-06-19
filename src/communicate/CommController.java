package communicate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CommController {
    @FXML
    public Button send;

    @FXML
    /**
     * 编辑消息区域
     */
    public TextField chatArea = new TextField();

    @FXML
    /**
     * 消息显示区域
     */
    public TextArea msgArea = new TextArea();

    public Boolean isSending = false;

    private static CommController instance;

    public static CommController getInstance() {
        return instance;
    }

    public CommController() {
        instance = this;
    }

    public void sendMsg() {
        isSending = true;
    }
}
