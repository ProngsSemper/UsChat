package communicate;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 使用多线程封装接收端
 */
public class Receive implements Runnable {
    private DataInputStream dis;
    private Socket client;
    private boolean isRunning=true;

    public Receive(Socket client) {
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
            System.out.println("接收异常");
            release();
        }
        return msg;
    }

    @Override
    public void run() {
        while (isRunning){
            String msg = receive();
            if (!msg.equals("")){
                System.out.println(msg);
            }
        }
    }

    private void release() {
        this.isRunning = false;
        ReUtils.close(dis, client);
    }
}
