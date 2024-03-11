package JAVA.test_server_client_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class test_client {
    private static BufferedReader reader = null;
    private static BufferedWriter writer = null;
    private static Scanner input = new Scanner(System.in);
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        try {
            // Connect to localhost on port 8080
            Socket socket = new Socket("localhost", 8080);

            System.out.println("connessione avvenuta\n");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            

            //code
            System.out.println(recieveString());
            
            // Close the socket and streams
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private boolean sendString(String message) {
        try{
            this.writer.write(message, 0, message.length());
            this.writer.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }*/

    private static String recieveString(){
        try{
            return reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        
    }
}
