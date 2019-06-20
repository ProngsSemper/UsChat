package communicate;

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
            if (!"".equals(msg)) {
                LoginFace.getInstance().msgArea.appendText("\n\n" + msg);
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
