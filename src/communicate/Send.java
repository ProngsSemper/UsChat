package communicate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Prongs
 */
public class Send implements Runnable {

    private BufferedReader console;

    private DataOutputStream dos;
    private Socket client;
    private boolean isRunning = true;
    private String name;

    /**
     * 创建构造方法，让客户端可以开启发送线程
     * @param client
     * @param name
     */
    Send(Socket client, String name) {
        console = new BufferedReader(new InputStreamReader(System.in));
        this.client = client;
        this.name = name;
        try {
            dos = new DataOutputStream(client.getOutputStream());
            //发送用户名
            send(name);
        } catch (IOException e) {
            System.out.println("客户端异常");
            this.release();
        }
    }

    /**
     * 获得聊天区域消息
     * @return
     */
    private String getStrFromSendText() {

        return LoginFace.getInstance().chatArea.getText();
//        try {
//            return console.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
    }

    /**
     * 发送方法
     * @param msg
     */
    private void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            System.out.println("客户端发送异常");
            release();
        }
    }

    /**
     * 当消息不为空时发送消息并将聊天区域清空，将发送状态改为false
     * 调用sleep方法为防止溢出（参考群内同学提问并收到的解决方案）
     */
    @Override
    public void run() {
        while (isRunning) {
            String message = getStrFromSendText();
            if (!message.equals("") && LoginFace.getInstance().isSending) {
                send(message);
                LoginFace.getInstance().chatArea.clear();
                LoginFace.getInstance().isSending = false;
            }
//            if (!message.equals("")){
//                send(message);
//            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放资源
     */
    private void release() {
        this.isRunning = false;
        ReUtils.close(dos, client);
    }
}
