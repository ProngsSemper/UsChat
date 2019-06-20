package communicate;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 使用多线程封装接收端
 *
 * @author Prongs
 */
class Receive implements Runnable {
    private DataInputStream dis;
    private Socket client;
    private boolean isRunning = true;

    /**
     * 创建构造方法让客户端开启线程
     *
     * @param client
     */
    Receive(Socket client) {
        this.client = client;
        try {
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            release();
        }
    }

    private String receive() {
        String msg = "";
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            System.out.println("客户端接收异常");
            release();
        }
        return msg;
    }

    /**
     * 将消息写入消息框
     */
    @Override
    public void run() {
        while (isRunning) {
            String msg = receive();
            if (msg.startsWith("Online")) {
                int start = 6;
                int end = 0;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        CommFaceContorller.getInstance().strList.clear();
                    }
                });

                while (msg.indexOf(",", start) != -1) {
                    end = msg.indexOf(",", start + 1);
                    if (end == -1) {
                        break;
                    }
                    String name =msg.substring(start + 1, end);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            CommFaceContorller.getInstance().strList.add(name);
                        }
                    });
                    start = end;
                }
                String name = msg.substring(start + 1);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        CommFaceContorller.getInstance().strList.add(name);
                        CommFaceContorller.getInstance().onlineUsers.setItems(CommFaceContorller.getInstance().strList);
                    }
                });

            } else if (!"".equals(msg)) {
                CommFaceContorller.getInstance().msgArea.appendText(msg + "\n\n");
            }

        }
    }

    /**
     * 释放资源方法
     */
    private void release() {
        this.isRunning = false;
        ReUtils.close(dis, client);
    }
}
