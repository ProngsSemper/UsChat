package communicate;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server implements Runnable {
    private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<Channel>();

    @Override
    public void run() {
        try {
            Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Server() throws IOException {
        System.out.println("-----Server-----");
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
        private boolean isRunning;
        private String name;

        public Channel(Socket client) {
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
                System.out.println("构建异常");
                release();
            }
        }

        private String receive() {
            String message = "";
            try {
                message = dis.readUTF();
            } catch (Exception e) {
                System.out.println("接收异常");
                release();
            }
            return message;
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

        private void sendOthers(String msg, boolean isSys) {
            //判断是否有@
            Boolean isPrivate = msg.startsWith("@");
            if (isPrivate) {
                int index = msg.indexOf(":");
                String targetName = msg.substring(1, index);
                msg = msg.substring(index + 1);
                for (Channel other : all) {
                    if (other.name.equals(targetName)) {
                        other.send(this.name + "私聊你：" + msg);
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

        private void release() {
            this.isRunning = false;
            ReUtils.close(dis, dos, client);
            all.remove(this);
            sendOthers(this.name + "离开聊天室", false);
        }

        @Override
        public void run() {
            while (isRunning) {
                String msg = receive();
                if (!msg.equals("")) {
                    sendOthers(msg, false);
                }
            }
        }
    }

}
