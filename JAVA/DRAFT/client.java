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

    public static void recieveFile(String nomeFile){
        try {
            InputStream inputStream = socket.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(nomeFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.close();
            return;
        } catch (IOException e) {
            e.printStackTrace();
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
                    query();
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

    @SuppressWarnings("resource")
    private static void query() {
        Scanner input = new Scanner(System.in);
        String message;
        String box;

        System.out.println(recieveString()); //inserisci cod-corso
        message = input.nextLine();
        sendString(message);

        System.out.println(recieveString()+"\n(exit for stop)"); //inserisci campi-corso
        message="";
        do {
            box = input.nextLine();
            if (!box.equals("exit")) {
                message += box + ",";
            }
        } while (!box.equals("exit"));
        if(message.equals("")) message="0";
        sendString(message); //campi corso richiesti
        
        /*----------- */
        
        System.out.println(recieveString()+"(no-need fore none)"); //inserisci cod-prof
        message = input.nextLine();
        if(message.equals("no-need")){
            sendString("0");
        }else{
            sendString(message);

            System.out.println(recieveString()+"\n(exit for stop)"); //inserisci campi-prof
            message="";
            do {
                box = input.nextLine();
                if (!box.equals("exit")) {
                    message += box + ",";
                }
            } while (!box.equals("exit"));
            if(message.equals("")) message="0";
            sendString(message); //campi prof richiesti
        }

        //----------------

        System.out.println(recieveString()+"(no-need fore none)"); //inserisci matricola studente
        message = input.nextLine();
        if(message.equals("no-need")){
            sendString("0");
        }else{
            sendString(message);

            System.out.println(recieveString()+"\n(exit for stop)"); //inserisci campi studente
            message="";
            do {
                box = input.nextLine();
                if (!box.equals("exit")) {
                    message += box + ",";
                }
            } while (!box.equals("exit"));
            if(message.equals("")) message="0";
            sendString(message); //campi studente richiesti
        }

        recieveFile("risultato.xml");
        sendString("ok");
        return; //problema non va a ristampare il menu
    }
}