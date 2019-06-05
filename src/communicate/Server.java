package communicate;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    static private CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<Channel>();

    public Server() throws IOException {
        System.out.println("----Server----");
        ServerSocket server = new ServerSocket(7888);
        while (true) {
            Socket client = server.accept();
            System.out.println("一个客户端建立了连接");
            Channel c = new Channel(client);
            all.add(c);
            new Thread(c).start();
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
                this.send("欢迎你的到来");
                sendOthers(this.name + "来到了聊天室", true);
            } catch (IOException e) {
                System.out.println("构建异常");
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

        private void send(String msg) {
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                System.out.println("发送异常");
                release();
            }
        }

        /**
         * 群聊
         */
        private void sendOthers(String msg, boolean isSys) {
            boolean isPrivate = msg.startsWith("@");
            if (isPrivate) {
                int idx = msg.indexOf("：");
                String targetName = msg.substring(1, idx);
                msg = msg.substring(idx + 1);
                for (Channel other : all) {
                    if (other.name.equals(targetName)) {
                        other.send(this.name + "悄悄地对你说：" + msg);
                        break;
                    }
                }
            } else {
                for (Channel other : all) {
                    if (other == this) {
                        continue;
                    }
                    if (!isSys) {
                        other.send(this.name + "对所有人说" + msg);
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
            sendOthers(this.name + "离开聊天室", true);
        }

        @Override
        public void run() {
            while (isRunning) {
                String msg = receive();
                if (!msg.equals("")) {
//                    send(msg);
                    sendOthers(msg, false);
                }
            }
        }
    }
}
