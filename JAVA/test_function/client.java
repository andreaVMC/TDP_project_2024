package JAVA.test_function;

import java.io.*;
import java.net.Socket;


@SuppressWarnings("unused")
public class client {
    public static void main(String[] args) {
        try {
            // Connect to localhost on port 8080
            Socket socket = new Socket("localhost", 8080);
            System.out.println("connessione avvenuta\n");
            
            //ricevere messaggi dal server
            System.out.println(receiveString(socket.getInputStream()));
            //mandare messaggi al server



            //chiudo la connessione
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendString(String message, OutputStream outputStream) {
        try {
            outputStream.write(message.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String receiveString(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            String box="";

            while ((line = reader.readLine()) != null) {
                box += line+'\n';
            }

            return box;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
