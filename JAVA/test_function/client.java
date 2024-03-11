package JAVA.test_function;

import java.io.*;
import java.net.Socket;


@SuppressWarnings("unused")
public class client {
    static Socket socket;
    public static void main(String[] args) {
        try {
            // Connect to localhost on port 8080
            socket = new Socket("localhost", 8080);
            System.out.println("connessione avvenuta\n");
            
            //ricevere messaggi dal server
            System.out.println(receiveString(socket.getInputStream()));
            System.out.println("--------------------");
            System.out.println(receiveString(socket.getInputStream()));
            //mandare messaggi al server

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String receiveString(InputStream inputStream) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }
            return buffer.toString("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}