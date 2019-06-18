package communicate;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Send implements Runnable {

    private DataOutputStream dos;
    private Socket client;
    private boolean isRunning = true;
    private String name;

    public Send(Socket client, String name) {
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

    public String getStrFromSendText() {

        return CommController.getInstance().chatArea.getText();
    }

    private void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            System.out.println("发送异常");
            release();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            String message = getStrFromSendText();
            if (!message.equals("") && CommController.getInstance().isSending) {
                send(message);
                CommController.getInstance().chatArea.clear();
                CommController.getInstance().isSending = false;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //释放资源
    private void release() {
        this.isRunning = false;
        ReUtils.close(dos, client);
    }
}
