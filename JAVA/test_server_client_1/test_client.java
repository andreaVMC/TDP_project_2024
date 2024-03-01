package JAVA.test_server_client_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class test_client {
    public static void main(String[] args) {
        try {
            // Connect to localhost on port 8080
            Socket socket = new Socket("localhost", 8080);





            // Create input and output streams for the socket
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Send a message to the server
            out.println("altro");

            // Receive and print the response from the server
            String response = in.readLine();
            System.out.println("Server response: " + response);

            // Close the socket and streams
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
