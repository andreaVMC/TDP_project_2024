package JAVA.DRAFT;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.*;

@SuppressWarnings("unused")
public class thread extends Thread {
    private static String filePath;
    private static Socket client;

    public thread(String filePath, Socket client) throws IOException {
        thread.filePath = filePath;
        thread.client = client;
    }

    @Override
    public void run() {
        //code
        int selection = 0;
        do{
            selection = menu(); //0=exit, 1=query, 2=append, 3=edit, 4=delete
            System.out.println("il client ha svolto la funzione: "+selection);
        }while(selection!=0);


        // Close the connection
        System.out.println("chiusura connessione");
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean sendMessage(String message) {
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

    private boolean sendMessage(File file){
        FileInputStream fileInputStream=null;
        try {
            byte[] data = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(data);
            OutputStream outputStream = client.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String recieveString() {
        try {
            InputStream inputStream = thread.client.getInputStream();
            byte[] data = new byte[1024];
            inputStream.read(data);
            return new String(data).trim(); //aggiunto trim per eliminare gli spazi bianchi -> controllare se funziona
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int menu() {
        String message = "\n\nMENU:\nlog_in: effettua l'accesso\nlog_out: esci dall'account\nquery: fai una richiesta\nappend: aggiugni dati\nedit: modifica dati \ndelete:cancella dati \nexit: chiudi la connessione";
        String selection="";
        do {
            sendMessage(message);
            selection = recieveString(); //il messaggio ricevuto è una stringa che pero ha sempre lunghezza di 1024 come il buffer di byte, quindi non è totalmente la stessa cosa
            if (selection.equals("query")) {
                query();
                return 1;
            } else if (selection.equals("append")) {
                return 2;
            } else if (selection.equals("edit")) {
                edit();
                return 3;
            } else if (selection.equals("delete")) {
                return 4;
            } else if (selection.equals("log_in")) {
                if(log_in()){
                    sendMessage("autenticato");
                }else{
                    sendMessage("non_autenticato");
                }
                return 5;
            } else if (selection.equals("log_out")) {
                return 6;
            } else if (selection.equals("exit")) {
                return 0;
            }else if (selection.equals("none")) {
                continue;
            }
        } while (true);
    }

    private static Element getRoot() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(filePath));
            return document.getDocumentElement();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> identificaNode(String codCorso, String codProf, String matricola, String campo){ //se un campo è uguale a 0 vuol dire che non è richiesto -> matricole e codice != 0
        Element root=getRoot();
        NodeList lista=root.getChildNodes();
        Node corso=null;
        String[] campi=null;
        List<String> elements = new ArrayList<>();
        if (!campo.equals("0")) {
            campi = campo.split(",");
        }


        for(int i=0; i<lista.getLength(); i++){
            Node node=lista.item(i);
            if(node.getNodeType()==Node.ELEMENT_NODE && node.getNodeName().equals("corso")){
                NamedNodeMap n = node.getAttributes();
                if(n.getNamedItem("codice").getNodeValue().equals(codCorso)){
                    corso=node; //trovo il corso indicato
                }
            }
        }

        
        if(corso==null){
            System.out.println("corso non trovato");
            return null;
        }
        
        if(codProf.equals("0") && matricola.equals("0") && campi!=null){
                for (int i = 0; i < campi.length; i++) {
                    NodeList childNodes = corso.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node childNode = childNodes.item(j);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals(campi[i])) {
                            elements.add(childNode.getTextContent());
                        }
                    }
                }
        }else if((!codProf.equals("0")) && matricola.equals("0") && campi!=null){
            lista=corso.getChildNodes();
            Node prof=null;

            for(int i=0; i<lista.getLength(); i++){
                Node node=lista.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE && node.getNodeName().equals("professore")){
                    NamedNodeMap n = node.getAttributes();
                    if(n.getNamedItem("codice").getNodeValue().equals(codCorso)){
                        prof=node; //trovo il prof indicato
                    }
                }
            }

            for (int i = 0; i < campi.length; i++) {
                NodeList childNodes = prof.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals(campi[i])) {
                        elements.add(childNode.getTextContent());
                    }
                }
            }

        }else if((!matricola.equals("0")) && codProf.equals("0") && campi!=null){
            lista=corso.getChildNodes();
            Node studente=null;

            for(int i=0; i<lista.getLength(); i++){
                Node node=lista.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE && node.getNodeName().equals("studenti")){
                    lista=node.getChildNodes();
                    break;
                }
            }

            for(int i=0; i<lista.getLength(); i++){
                Node node=lista.item(i);
                
                if(node.getNodeType()==Node.ELEMENT_NODE && node.getNodeName().equals("studente")){
                    NamedNodeMap n = node.getAttributes();
                    if(n.getNamedItem("matricola").getNodeValue().equals(matricola)){
                        studente=node; //trovo lo studente indicate
                    }
                }
            }

            for (int i = 0; i < campi.length; i++) {
                NodeList childNodes = studente.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals(campi[i])) {
                        elements.add(childNode.getTextContent());
                    }
                }
            }
        }

        return elements;
    }

    private static String getElementsNode(int select){ //restituisce tutti gli elementi di un nodo , 1=corso, 2=professore, 3=studente
        Element root=getRoot();
        if (select==1){
            return "nome_corso,anno,crediti";
        }else if(select==2){
            return "nome,cognome,materia,data_nascita,email,password";
        }else if(select==3){
            return "nome,cognome,data_nascita,citta_nascita,sesso,email,stato_corso,voto,password";
        }else{
            return "errore";
        }
    }

    private void query() {
        sendMessage("Inserisci il codice del corso:\n");
        String codCorso = recieveString();

        sendMessage(getElementsNode(1));
        String campiCorso = recieveString();

        sendMessage("Inserisci il codice del professore:\n");
        String codProf = recieveString();
        String campiProf="";
        if(!codProf.equals("0")){
            sendMessage(getElementsNode(2));
            campiProf = recieveString();
        }else{
            campiProf="0";
        }

        sendMessage("Inserisci la matricola studente:\n");
        String codStudente = recieveString();
        String campiStudente="";
        if(!codStudente.equals("0")){
            sendMessage(getElementsNode(3));
            campiStudente = recieveString();
        }else{
            campiStudente="0";
        }


        System.out.println("codice corso: "+codCorso+"\ncampi corso: "+campiCorso+"\ncodice professore: "+codProf+"\ncampi professore: "+campiProf+"\ncodice studente: "+codStudente+"\ncampi studente: "+campiStudente);
    
        List<String> elementsCorso = identificaNode(codCorso, "0", "0", campiCorso);

        List<String> elementsProf = identificaNode(codCorso, codProf, "0", campiProf);

        List<String> elementsStudente = identificaNode(codCorso, "0", codStudente, campiStudente);

        sendMessage(createFileQuery(codCorso, codProf, codStudente, campiCorso, campiProf, campiStudente, elementsCorso, elementsProf, elementsStudente));

        if(recieveString().equals("ok")){
            System.out.println("file inviato correttamente");
        }else{
            System.out.println("errore nell'invio del file");
        }
    }

    private File createFileQuery(String codCorso, String codProf, String matricola, String campoCorso, String campoProf, String campoStudente,List<String> elementsCorso, List<String> elementsProf, List<String> elementsStudente){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        
        String[] campiCorso = null;
        if (!campoCorso.equals("0")) {
            campiCorso = campoCorso.split(",");
        }
        String[] campiProf = null;
        if (!campoProf.equals("0")) {
            campiProf = campoProf.split(",");
        }
        String[] campiStudente = null;
        if (!campoStudente.equals("0")) {
            campiStudente = campoStudente.split(",");
        }

        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            // Root element
            Element rootElement = doc.createElement("richiesta");
            doc.appendChild(rootElement);
            
            // Append the child corso with its attribute "codice" and its elementsCorso
            if(!codCorso.equals("0")){
                Element corso = doc.createElement("corso");
                corso.setAttribute("codice", codCorso);
                if(campiCorso!=null){
                    for (int i = 0; i < campiCorso.length; i++) {
                        Element campo = doc.createElement(campiCorso[i]);
                        campo.appendChild(doc.createTextNode(elementsCorso.get(i)));
                        corso.appendChild(campo);
                    }
                }

                if(!codProf.equals("0")){
                    Element prof = doc.createElement("professore");
                    prof.setAttribute("codice", codProf);
                    if(campiProf!=null){
                        for (int i = 0; i < campiProf.length; i++) {
                            Element campo = doc.createElement(campiProf[i]);
                            campo.appendChild(doc.createTextNode(elementsProf.get(i)));
                            prof.appendChild(campo);
                        }
                    }
                    corso.appendChild(prof);
                }

                if(!matricola.equals("0") ){
                    Element studente = doc.createElement("studente");
                    studente.setAttribute("matricola", matricola);
                    if(campiStudente!=null){
                        for (int i = 0; i < campiStudente.length; i++) {
                            Element campo = doc.createElement(campiStudente[i]);
                            campo.appendChild(doc.createTextNode(elementsStudente.get(i)));
                            studente.appendChild(campo);
                        }
                    }
                    corso.appendChild(studente);
                }

                rootElement.appendChild(corso);
            }
            // Save the document to a file or perform other operations
            File fileRichiesta = new File("query.xml");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fileRichiesta);

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
            
            transformer.transform(source, result);
        
            return fileRichiesta;
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            
        }
        return null;
    }

    private boolean log_in() {
        String role=recieveString();
        String cod=recieveString();
        String password = recieveString();
    

        Element root = getRoot();
        NodeList lista = root.getChildNodes();

        for(int i=0;i<lista.getLength();i++){
            //controlla se l'elemento della lista è un elemento corso
            Node node=lista.item(i);
            if(node.getNodeType()==Node.ELEMENT_NODE && node.getNodeName().equals("corso")){
                NodeList childNodes = node.getChildNodes();
                if(role.equals("professore")){
                    //controlla quali elementi del childnodes è un elemento professore
                    for(int j=0;j<childNodes.getLength();j++){
                        Node childNode=childNodes.item(j);
                        if(childNode.getNodeType()==Node.ELEMENT_NODE && childNode.getNodeName().equals("professore")){
                            NamedNodeMap n = childNode.getAttributes();
                            if(n.getNamedItem("codice").getNodeValue().equals(cod)){
                                System.out.println("fino al codice c'è");
                                NodeList childNodesProf = childNode.getChildNodes();
                                for(int k=0;k<childNodesProf.getLength();k++){
                                    Node childNodeProf=childNodesProf.item(k);
                                    if(childNodeProf.getNodeType()==Node.ELEMENT_NODE && childNodeProf.getNodeName().equals("password")){
                                        System.out.println("password rilevate:"+childNodeProf.getTextContent());
                                        if(childNodeProf.getTextContent().equals(password)){
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if(role.equals("studente")){
                    for(int j=0;j<childNodes.getLength();j++){
                        Node childNode=childNodes.item(j);
                        if(childNode.getNodeType()==Node.ELEMENT_NODE && childNode.getNodeName().equals("studenti")){
                            NodeList childNodesStudenti = childNode.getChildNodes();
                            for(int k=0;k<childNodesStudenti.getLength();k++){
                                Node childNodeStudenti=childNodesStudenti.item(k);
                                if(childNodeStudenti.getNodeType()==Node.ELEMENT_NODE && childNodeStudenti.getNodeName().equals("studente")){
                                    NamedNodeMap n = childNodeStudenti.getAttributes();
                                    if(n.getNamedItem("matricola").getNodeValue().equals(cod)){
                                        NodeList childNodesStudente = childNodeStudenti.getChildNodes();
                                        for(int l=0;l<childNodesStudente.getLength();l++){
                                            Node childNodeStudente=childNodesStudente.item(l);
                                            if(childNodeStudente.getNodeType()==Node.ELEMENT_NODE && childNodeStudente.getNodeName().equals("password")){
                                                if(childNodeStudente.getTextContent().equals(password)){
                                                    return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    private static void edit(){
        String codCorso = riceviCodici(1);
        String[] campiCorso = riceviCampi(1).split(",");

        String codProf = riceviCodici(2);
        String[] campiProf=null;
        if(!codProf.equals("0")){
            campiProf = riceviCampi(2).split(",");
        }

        String codStudente = riceviCodici(3);
        String[] campiStudente=null;
        if(!codStudente.equals("0")){
            campiStudente = riceviCampi(3).split(",");
        }


        String[] newCorso = null;
        if(!campiCorso[0].equals("0")){
            newCorso = new String[campiCorso.length];
            sendMessage("Inserisci i nuovi valori per Corso:\n");
            recieveString();
            for(int i=0;i<campiCorso.length;i++){
                sendMessage(campiCorso[i]+":");
                newCorso[i]=recieveString();
            }
        }
        sendMessage("stop");

 
        String[] newProf = null;
        if(campiProf!=null){
            newProf = new String[campiProf.length];
            sendMessage("Inserisci i nuovi valori per Professore:\n");
            recieveString();
            for(int i=0;i<campiProf.length;i++){
                sendMessage(campiProf[i]+":");
                newProf[i]=recieveString();
            }
        }
        sendMessage("stop");

        String[] newStudente = null;
        if(campiStudente!=null){
            newStudente = new String[campiStudente.length];
            if(!campiStudente[0].equals("0")){
                sendMessage("Inserisci i nuovi valori per Studente:\n");
                recieveString();
                for(int i=0;i<campiStudente.length;i++){
                    sendMessage(campiStudente[i]+":");
                    newStudente[i]=recieveString();
                }
            }
        }
        sendMessage("stop");

        //update the xml file and save it

        //fiche ci sono figli trova i campi corrispondenti ed aggiornali poi salva l'alabero xml in un file con il medesimo nome del file sorgente
        

    }

    private static String riceviCodici(int option){
        if(option==1){
            sendMessage("Inserisci il codice del corso:\n");
        }else if(option==2){
            sendMessage("Inserisci il codice del professore:\n");
        }else if(option==3){
            sendMessage("Inserisci la matricola studente:\n");
        }
        return recieveString();
    }

    private static String riceviCampi(int option){
        sendMessage(getElementsNode(option));
        return recieveString();
    }
}
