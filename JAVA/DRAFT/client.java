package JAVA.DRAFT;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


@SuppressWarnings("unused")
public class client {
    static Socket socket;
    public static void main(String[] args) {
        try {
            // Connect to localhost on port 8080
            socket = new Socket("localhost", 8080);
            System.out.println("connessione avvenuta\n");

            //code
            int selection=0;
            do{
                System.out.println(recieveString());
                selection=menu_selection();
                System.out.println(selection);
            }while(selection!=0);
            

            // Close the connection
            System.out.println("chiusura connessione");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String recieveString(){
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] data = new byte[1024];
            inputStream.read(data);
            return new String(data).trim();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean sendString(String message){
        try {
            byte[] data = message.getBytes();
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int menu_selection() {
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);
        String selection;
        do {
            selection = input.nextLine();
            switch (selection) {
                case "query":
                    sendString(selection);
                    return 1;
                case "append":
                    sendString(selection);
                    return 2;
                case "edit":
                    sendString(selection);
                    return 3;
                case "delete":
                    sendString(selection);
                    return 4;
                case "exit":
                    sendString(selection);
                    return 0;
                default:
                    System.out.println("Selezione non valida");
                    break;
            }
        } while (true);
    }
}