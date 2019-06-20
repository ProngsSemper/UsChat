package communicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * @author Prongs
 */
public class CommFaceContorller {
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
    private static CommFaceContorller instance;

    static CommFaceContorller getInstance() {
        return instance;
    }

    public CommFaceContorller() {
        instance = this;
        onlineUsers.setItems(strList);
    }

    /**
     * 发送消息
     */
    public void sendMsg() {
        isSending = true;
    }
    
}
