package communicate;

import db.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.LoginUsers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerFaceController {
    @FXML
    public ListView<String> onlineUsers;

    String sql = "select loginUsers from loginusers";
    DBUtil dbUtil = new DBUtil();
    ResultSet result = null;
    ArrayList list = new ArrayList<>();
    ObservableList<String> strList = FXCollections.observableArrayList("白色");

//    {
//        try {
//            result = dbUtil.check(sql);
//            while (result.next()) {
//                LoginUsers loginUsers = new LoginUsers(result.getString(1));
//                list.add(loginUsers);
////                strList.addAll(list);
////                onlineUsers.setItems(strList);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        for (LoginUsers loginUsers:list){
//            strList = FXCollections.observableArrayList(loginUsers.getLoginUsers());
//            onlineUsers.setItems(strList);
//        }
//
//    }

}
