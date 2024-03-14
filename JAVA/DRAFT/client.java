package JAVA.DRAFT;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


@SuppressWarnings("unused")
public class client {
    static Socket socket;
    static int role=0;
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
            int bytesRead=0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                if(inputStream.available()==0) break;
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
                    if(role!=0){
                        sendString(selection);
                        query();
                        return 1;
                    }else{
                        System.out.println("non sei autenticato");
                        sendString("none"); //string per continuare
                        separate();
                        return -1;
                    }
                case "append":
                    sendString(selection);
                    return 2;
                case "edit":
                    sendString(selection);
                    return 3;
                case "delete":
                    sendString(selection);
                    return 4;
                case "log_in":
                    sendString(selection);
                    log_in();
                    return 5;
                case "log_out":
                    sendString(selection);
                    return 6;
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
        separate();
        Scanner input = new Scanner(System.in);
        String message;
        String box;

        System.out.println(recieveString()); //inserisci cod-corso
        message = input.nextLine();
        sendString(message);
        
        String[] campi = recieveString().split(",");
        System.out.println("inserisci campi corso:"); //inserisci campi-corso
        for(int i=0;i<campi.length;i++){
            System.out.println(campi[i]);
        }
        System.out.println("\"exit\" to end");
        message="";
        boolean flag=false;
        do {
            box = input.nextLine();
            if (!box.equals("exit")) {
                for(int i=0;i<campi.length;i++){
                    if(box.equals(campi[i])){
                        flag=true;
                        break;
                    }
                }
                if(flag){
                    message += box + ",";
                    flag=false;
                }else{
                    System.out.println("campo inesistente");
                }
            }
        }while (!box.equals("exit"));
        if(message.equals("")) message="0";

        sendString(message); //campi corso richiesti
        campi=null;
        /*----------- */
        separate();
        
        System.out.println(recieveString()+"(no-need fore none)"); //inserisci cod-prof
        message = input.nextLine();
        if(message.equals("no-need")){
            sendString("0");
        }else{
            sendString(message);

            campi = recieveString().split(",");
            System.out.println("inserisci campi prof:"); //inserisci campi-corso
            for(int i=0;i<campi.length;i++){
                System.out.println(campi[i]);
            }
            System.out.println("\"exit\" to end");


            message="";
            do {
                box = input.nextLine();
                if (!box.equals("exit")) {
                    for(int i=0;i<campi.length;i++){
                        if(box.equals(campi[i]) && flag==false){
                            flag=true;
                        }
                    }
                    if(flag){
                        message += box + ",";
                        flag=false;
                    }else{
                        System.out.println("campo inesistente");
                    }
                }
            } while (!box.equals("exit"));
            
            if(message.equals("")){
                message="0";
            }
            
            sendString(message); //campi prof richiesti
            campi=null;
        }

        //----------------
        separate();

        System.out.println(recieveString()+"(no-need fore none)"); //inserisci matricola studente
        message = input.nextLine();
        if(message.equals("no-need")){
            sendString("0");
        }else{
            sendString(message);

            campi = recieveString().split(",");
            System.out.println("inserisci campi studente:"); //inserisci campi-studente
            for(int i=0;i<campi.length;i++){
                System.out.println(campi[i]);
            }
            System.out.println("\"exit\" to end");


            message="";
            do {
                box = input.nextLine();
                if (!box.equals("exit")) {
                    for(int i=0;i<campi.length;i++){
                        if(box.equals(campi[i])){
                            flag=true;
                            break;
                        }
                    }
                    if(flag){
                        message += box + ",";
                        flag=false;
                    }else{
                        System.out.println("campo inesistente");
                    }
                }
            } while (!box.equals("exit"));
            if(message.equals("")) message="0";
            sendString(message); //campi studente richiesti
            campi=null;
        }

        recieveFile("risultato.xml"); // non va avanti
        System.out.println("dati salvati");
        sendString("ok");
        separate();
        return; //problema non va a ristampare il menu
    }

    private static void separate(){
        System.out.print("\n-+-+-+-+-+-\n\n");
    }

    private static void log_in(){
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);

        System.out.println("ruolo:\nprofessore\nstudente");
        String ruolo=input.nextLine();
        sendString(ruolo);

        System.out.println("matricola/codice:");
        sendString(input.nextLine());

        System.out.println("password:");
        sendString(input.nextLine());

        if(recieveString().equals("autenticato")){
            if(ruolo.equals("professore")) role=2;
            else if( ruolo.equals("studente")) role=1;
            System.out.println("autenticato");
        }else{
            System.out.println("password o codice errati");
        }
    }
}