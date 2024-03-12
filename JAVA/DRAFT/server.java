package JAVA.DRAFT;

import java.io.IOException;
import java.net.*;

public class server {
    public static void main(String[] args) {
        // Create a ServerSocket object
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080); // Specify the port number you want to use

            System.out.println("Server started. Listening on port 8080...");

            while (true) {
                // Accept client connections
                Socket clientSocket = serverSocket.accept();

                // Handle client request in a separate thread
                thread thread = new thread("XML_XSD/seconda/test_dati_1.xml", clientSocket);
                System.out.println("connessione avvenuta");
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
