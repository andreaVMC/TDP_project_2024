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
            System.out.println("ooooo "+selection);
        }while(selection!=0);

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
            return new String(data);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int menu() {
        String message = "query: fai una richiesta\nappend: aggiugni dati\nedit: modifica dati \ndelete:cancella dati \nexit: chiudi la connessione";
        String selection="";
        do {
            sendMessage(message);
            selection = recieveString(); //il messaggio ricevuto è una stringa che pero ha sempre lunghezza di 1024 come il buffer di byte, quindi non è totalmente la stessa cosa
            String ciao = new String(selection);
            if (selection.equals("query")) {
                System.out.println("selection blabla:"+selection);
                return 1;
            } else if (selection.equals("append")) {
                System.out.println("selection blabla:"+selection);
                return 2;
            } else if (selection.equals("edit")) {
                System.out.println("selection blabla:"+selection);
                return 3;
            } else if (selection.equals("delete")) {
                System.out.println("selection blabla:"+selection);
                return 4;
            } else if (selection.equals("exit")) {
                System.out.println("selection blabla:"+selection);
                return 0;
            }
        } while (true);
    }
}