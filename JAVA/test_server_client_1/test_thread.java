package JAVA.test_server_client_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class test_thread extends Thread {
    private final String filePath;
    private final OutputStream outputStream;
    private final InputStream inputStream;

    public test_thread(String filePath, OutputStream outputStream2, InputStream inputStream2) {
        this.filePath = filePath;
        this.outputStream = outputStream2;
        this.inputStream = inputStream2;
    }

    @Override
    public void run() {

        // Read the input string from the client
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String inputString = reader.readLine();

            
            if(inputString.equals("dati")){
                try {
                    // Read the XML file
                    File file = new File(filePath);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document document = builder.parse(fileInputStream);
                    fileInputStream.close();

                    // Convert the XML document to a string
                    String xmlString = convertDocumentToString(document);

                    // Send the XML string to the client
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(xmlString);
                    objectOutputStream.flush();
                    objectOutputStream.close();

                } catch (IOException | javax.xml.parsers.ParserConfigurationException | org.xml.sax.SAXException e) {
                    e.printStackTrace();

                }
            }else{
                String xmlString = "ok";

                // Send the XML string to the client
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(xmlString);
                objectOutputStream.flush();
                objectOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String convertDocumentToString(Document document) {
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
    }
}
