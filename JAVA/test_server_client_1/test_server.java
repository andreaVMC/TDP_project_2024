package JAVA.test_server_client_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class test_server {
    public static void main(String[] args) {
        try {
            // Create a ServerSocket object
            ServerSocket serverSocket = new ServerSocket(8080); // Specify the port number you want to use

            System.out.println("Server started. Listening on port 8080...");

            while (true) {
                // Accept client connections
                Socket clientSocket = serverSocket.accept();

                // Handle client request in a separate thread
                test_thread thread = new test_thread("XML_XSD/test_dati_1.xml", clientSocket.getOutputStream(), clientSocket.getInputStream());
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
