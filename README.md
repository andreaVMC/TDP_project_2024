# Gestione Università

Questo progetto è un'applicazione Java per la gestione di un'università. Utilizza XML e XSD per la memorizzazione dei dati relativi ad alunni, insegnanti, corsi e voti.

## Requisiti

- Java Development Kit (JDK) installato
- Un editor di testo o un ambiente di sviluppo integrato (IDE) per modificare e compilare il codice sorgente

## Configurazione

1. Clona il repository o scarica il codice sorgente.
2. Apri il progetto nel tuo IDE preferito.
3. Assicurati di avere il file `universita.xml` che contiene i dati dell'università.
4. Verifica che il file `universita.xsd` sia presente e corrisponda allo schema XML per la validazione dei dati.
5. Configura il tuo ambiente di sviluppo per utilizzare il JDK corretto.

## Utilizzo

1. Esegui il file `Main.java` per avviare l'applicazione.
2. Segui le istruzioni a schermo per interagire con l'applicazione.
3. Puoi aggiungere, modificare o eliminare alunni, insegnanti, corsi e voti utilizzando i comandi disponibili.

## Contributi

Sono benvenuti i contributi a questo progetto. Se desideri apportare modifiche, apri una pull request e descrivi le modifiche proposte.

## Licenza

Questo progetto è distribuito con la licenza MIT. Per ulteriori informazioni, consulta il file `LICENSE`.

## Contatti

Per domande o commenti, contattami all'indirizzo email [vaccaroandrea1227@gmail.com](mailto:vaccaroandrea1227@gmail.com).

## To-Do list

- [x] Prototipo file xml con xsd validatore.
- [x] Programma che legge file xml.
- [x] Server e Client TCP ed inviare file xml al client.
- [x] Gestire client con thread.
- [x] Creare menu nel Thread per gestire richieste client.
- [ ] Creazione file richiesta del client.
- [ ] Invio e ricezione del file richiesta.
- [ ] Interfaccia JavaSwing Server e Client.

----------------------------

# Aggiornamenti

Aggiornamenti di ciò che viene fatto al processo per mantenere la tracciabilità di aggiornamenti/risoluzione criticità del programma

## 29/02/2024

- creata cartella XML/XSD e JAVA

- testati primi prototipi per file di xml/xsd per gestire e validare dati università

- test semplice file in java per la lettura di un file xml, che mostra l'abero degl'elementi presenti

- test_server_client_1 -> un client si collega ad un server TDP, che lo reindirizza ad un thread che gli inviera il nome di tutti i nodi presenti nel file xml 

## 01/03/2024

- menu nel thread server per gestione richiesta client -> prossimo passo implementare tutte le possibili opzioni attraverso funzioni
- bug: la stringa ricevuta dal client contiene una sotto stringa costante di caratteri errati: "??♣t☻" -> da risolvere

## 11/03/2024

- aggiunto "CONSEGNA.md"
- nuovi file per creare e testare tutte le funzioni necessarie per il programma
- rallentami nel progetto dovuti a problemi di codice ed interpretazione di esso, da revisionare in maniera categoria