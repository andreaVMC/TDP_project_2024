package JAVA.test_function;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;



@SuppressWarnings("unused")
public class thread extends Thread {
    private final String filePath;
    private final OutputStream outputStream;
    private final InputStream inputStream;

    public thread(String filePath, OutputStream outputStream, InputStream inputStream) {
        this.filePath = filePath;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        //mandare messaggio al client
        sendString("io\nsono");
        //ricevere messaggio dal client
    }


    private void sendString(String message) { //da fare anche per client
        try {
            PrintWriter printWriter = new PrintWriter(this.outputStream);
            printWriter.println(message);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String receiveString(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)); //non inzializzare ogni volta (riguardare acche client)

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
