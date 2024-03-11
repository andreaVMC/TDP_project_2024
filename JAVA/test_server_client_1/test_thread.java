package JAVA.test_server_client_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.BufferedWriter;



public class test_thread extends Thread {
    private final String filePath;
    private final OutputStream outputStream;
    private final InputStream inputStream;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public test_thread(String filePath, OutputStream outputStream2, InputStream inputStream2) {
        this.filePath = filePath;
        this.outputStream = outputStream2;
        this.inputStream = inputStream2;
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.writer = new BufferedWriter(new OutputStreamWriter(outputStream2));
    }

    @Override
    public void run() {
        //code
        sendString("ciao");
        System.out.println("stringa mandata");
    }

    private boolean sendString(String message) {
        try{
            this.writer.write(message, 0, message.length());
            this.writer.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    String recieveString() {
        String box="",inputString="";
        try {
            while((box=reader.readLine())!=null){
                inputString+=box;
            }
            System.out.println(inputString);
            return inputString;
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }





    /*private String convertDocumentToString(Document document) {
        StringBuilder stringBuilder = new StringBuilder();
        NodeList nodeList = document.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                stringBuilder.append(element.getNodeName()).append(", ");
            }
        }
        return stringBuilder.toString();
    }*/
}
