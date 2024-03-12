package JAVA.DRAFT;

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
        //code
        int selection = 0;
        do{
            selection = menu();
            System.out.println(selection);
        }while(selection!=0);


        // Close the connection
        System.out.println("chiusura connessione");
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean sendMessage(String message) {
        try {
            byte[] data = message.getBytes();
            OutputStream outputStream = client.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String recieveString() {
        try {
            InputStream inputStream = client.getInputStream();
            byte[] data = new byte[1024];
            inputStream.read(data);
            return new String(data).trim(); //aggiunto trim per eliminare gli spazi bianchi -> controllare se funziona
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int menu() {
        String message = "MENU:\nquery: fai una richiesta\nappend: aggiugni dati\nedit: modifica dati \ndelete:cancella dati \nexit: chiudi la connessione";
        String selection="";
        do {
            sendMessage(message);
            selection = recieveString(); //il messaggio ricevuto è una stringa che pero ha sempre lunghezza di 1024 come il buffer di byte, quindi non è totalmente la stessa cosa
            if (selection.equals("query")) {
                return 1;
            } else if (selection.equals("append")) {
                return 2;
            } else if (selection.equals("edit")) {
                return 3;
            } else if (selection.equals("delete")) {
                return 4;
            } else if (selection.equals("exit")) {
                return 0;
            }
        } while (true);
    }
}