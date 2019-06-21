package communicate;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Prongs
 */
public class Server implements Runnable {

    private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<Channel>();

    @FXML
    public ListView<String> onlineUsers = new ListView<>();

    private static List<String> list = new ArrayList<>();
    private static ObservableList<String> strList;

    private static Server instance;

    private static Server getInstance() {
        return instance;
    }

    public Server() {
        instance = this;
    }

    /**
     * 显示在线用户列表
     *
     * @param onlineUsers
     */
    private void onlineUsers(ListView<String> onlineUsers) {
        for (Channel channel : all) {
            list.add(channel.name);
            strList = FXCollections.observableArrayList(list);
            onlineUsers.setItems(null);
            onlineUsers.setItems(strList);
        }

    }

    @Override
    public void run() {
        try {
            this.server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void server() throws IOException {
        System.out.println("-----server-----");
        ServerSocket server = new ServerSocket(12345);
        while (true) {
            Socket client = server.accept();
            System.out.println("一个客户端建立了连接");
            Channel channel = new Channel(client);
            all.add(channel);
            new Thread(channel).start();

        }
    }

    static class Channel implements Runnable {
        private DataInputStream dis;
        private DataOutputStream dos;
        private Socket client;
        boolean isRunning;
        String name;

        Channel(Socket client) {
            this.client = client;
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                isRunning = true;
                this.name = receive();
                this.send("欢迎" + name + "的到来");
                //发给其他人
                sendOthers(this.name + "进入聊天室", true);

            } catch (IOException e) {
                System.out.println("服务端构建异常");
                release();
            }
        }

        /**
         * 接收方法
         *
         * @return
         */
        private String receive() {
            String message = "";
            try {
                message = dis.readUTF();
                System.out.println(message);
            } catch (Exception e) {
                System.out.println("服务端接收异常");
                release();
            }
            return message;
        }

        private void send(String msg) {
            try {
                try {
                    /*
                    防止栈溢出错误
                     */
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                System.out.println("发送异常");
                release();
            }
        }

        /**
         * 当isSys为true时则为广播消息
         *
         * @param msg
         * @param isSys
         */
        private void sendOthers(String msg, boolean isSys) {
            boolean isPrivate = msg.startsWith("@");
            if (isPrivate) {
                /*
                  注意是中文输入状态下的冒号若用英文输入状态下的冒号程序会崩溃
                 */
                int idx = msg.indexOf("：");
                /*
                   本来想用检测到用来@其他用户的冒号是用英文输入状态下的冒号则将其改为
                   中文输入状态的冒号但是没有成功
                 */
//                int check = msg.indexOf(":");
//                if (check<idx){
//                    msg.replace(":","：");
//                }
                String targetName = msg.substring(1, idx);
                msg = msg.substring(idx + 1);
                for (Channel other : all) {
                    if (other.name.equals(targetName)) {
                        other.send(this.name + "私聊你说：" + msg);
                        break;
                    }
                }
            } else {
                for (Channel other : all) {
                    if (!isSys) {
                        other.send(this.name + "：\n" + msg);
                    } else {
                        other.send(msg);
                    }
                }
            }
        }

        /**
         * 释放资源方法
         * 释放资源同时更新服务端与客户端的在线用户列表
         */
        private void release() {
            this.isRunning = false;
            ReUtils.close(dis, dos, client);
            all.remove(this);
            //用户退出后从在线用户列表中删除此用户  存在问题：最后一个退出的用户无法从在线用户列表中删除
            list.clear();
            String Online = "Online";
            for (Channel channel : all) {
                Online += "," + channel.name;
            }
            for (Channel channel : all) {
                channel.send(Online);
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Server.getInstance().onlineUsers(Server.getInstance().onlineUsers);
                }
            });
            sendOthers(this.name + "离开聊天室", false);
        }

        @Override
        public void run() {
            while (isRunning) {
                /*
                此代码块用作实现客户端在线用户显示功能组成之一
                将所有的用户名按照"Online,user 1,user 2,...,user n"的格式发送给每个客户端
                 */
                String Online = "Online";
                for (Channel channel : all) {
                    Online += "," + channel.name;
                }
                send(Online);
                sendOthers(Online, true);
                /*
                 1.参考CSDN上对出现Not on FX application thread; currentThread = *的异常的解决方案
                 2.用户登录后用户名即可出现在用户列表中
                 */
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Server.getInstance().onlineUsers(Server.getInstance().onlineUsers);
                    }
                });
                list.clear();
                String msg = receive();
                if (!"".equals(msg)) {
                    sendOthers(msg, false);
                }
            }
        }
    }

}
