package JAVA.test_function;

import java.io.*;
import java.net.*;

@SuppressWarnings("unused")
public class thread extends Thread {
    private String filePath;
    private Socket client;

    public thread(String filePath, Socket client) throws IOException {
        this.filePath = filePath;
        this.client = client;
    }

    @Override
    public void run() {
        //mandare messaggio al client
        sendString("io\nsono");
        //ricevere messaggio dal client
        sendString("tu\nsei");
    }

    private void sendString(String message) {
        try {
            OutputStream outputStream = client.getOutputStream();
            outputStream.write(message.getBytes());
            outputStream.flush();
            System.out.println("Message sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
