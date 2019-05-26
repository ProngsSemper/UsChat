package signup;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {
    public Button Cancel;

    /**
     * 点击取消时可关闭窗口
     */
    public void exit() {
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
    }
}
