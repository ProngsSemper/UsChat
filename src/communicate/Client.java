package communicate;

import main.UserName;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public Client() throws IOException {
        System.out.println("----Client----");
        String name = UserName.name;
        Socket client = new Socket("localhost", 7888);
        Thread sendThread = new Thread(new Send(client,name));
        Thread receiveThread = new Thread(new Receive(client));
        sendThread.start();
        receiveThread.start();
    }
}
