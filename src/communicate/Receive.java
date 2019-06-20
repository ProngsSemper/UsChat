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
            /*
             当接收到服务端传进来的特殊格式时对消息进行分解并将用户名加入strList
             */
            if (msg.startsWith("Online")) {
                int start = 6;
                int end = 0;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //对strList进行更新
                        CommFaceContorller.getInstance().strList.clear();
                    }
                });

                while (msg.indexOf(",", start) != -1) {
                    //返回第一个"Online,"的逗号后面的逗号的索引位置
                    end = msg.indexOf(",", start + 1);
                    //索引完成后跳出循环以免进入死循环
                    if (end == -1) {
                        break;
                    }
                    /*
                    因使用了Platform.runLater的缘故需要定义String name来将用户名传入 下同
                    提取两个逗号之间的字符串当作用户名
                     */
                    String name = msg.substring(start + 1, end);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //把用户名传入strList
                            CommFaceContorller.getInstance().strList.add(name);
                        }
                    });
                    //让本次结束的位置成为下次开始的位置达到提取所有逗号前用户名的目的
                    start = end;
                }
                //提取完最后一个逗号前的用户名后还要提取最后一个逗号后面的用户名，下面就是把最后一个逗号后的字符串全部提取作最后的用户名
                String name = msg.substring(start + 1);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        CommFaceContorller.getInstance().strList.add(name);
                        //将用户名添加到列表
                        CommFaceContorller.getInstance().onlineUsers.setItems(CommFaceContorller.getInstance().strList);
                    }
                });

            } else if (!"".equals(msg)) {
                //普通消息的传输
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
