package communicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author Prongs
 */
public class CommFaceController {
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

    @FXML
    public ListView<String> onlineUsers = new ListView<>();
    ObservableList<String> strList = FXCollections.observableArrayList();
    /**
     * 判断发送状态，避免出现死循环现象
     */
    Boolean isSending = false;

    /**
     * 参考群里师兄讲解的单例方法，获取组件。
     */
    private static CommFaceController instance;

    static CommFaceController getInstance() {
        return instance;
    }

    public CommFaceController() {
        instance = this;
    }

    /**
     * 发送消息
     */
    public void sendMsg() {
        isSending = true;
    }

}
