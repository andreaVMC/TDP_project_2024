# Consegna
## Cose da fare per il progetto:
1. Struttura Server-Multiclient TCP in cui il client può collegarsi al server per richiedere
un servizio. Questo servizio potrebbe essere una raccolta di dati particolari, tipo
archivio libri, documenti aziendali, videoteche, oppure iscrizioni a corsi online ecc.
2. Ci deve essere un file XML con all’interno dei dati. Il documento deve avere le
conseguenti caratteristiche:
- almeno 3 livelli oltre l’elemento radice,
- almeno una restrizione per un tipo di elemento
- almeno un elemento deve avere un attributo
3. Ci deve comunque essere un file XSD che valida il file XML.
4. Il client ha la possibilità di richiedere la visualizzazione di un tipo di elemento del file
XML al server (Es: chiedo l’elemento titolo e il server risponde con tutti i titoli dei
libri/album). Il server creerà un nuovo file XML con elemento radice = <richiesta>,
che avrà come figli i contenuti dell’elemento richiesto.
5. Il client può aggiungere dati alla raccolta del file XML (Es: aggiungo un nuovo libro),
che dovrà comunque rispettare la struttura e le eventuali restrizioni dettate dallo
schema. Il server dovrà poi salvare il documento modificato.
Sarà poi necessario svolgere una presentazione di 15 minuti per spiegare il lavoro fatto.

La valutazione sarà fatta su:
- Completamento delle richieste
- Chiarezza del codice
- Chiarezza della presentazione ed esposizione
- Analisi delle criticità emerse sull’applicazione e/o sul lavoro personale
- Aggiunta di funzionalità o personalizzazioni legate al contesto scelto non richieste
dalla consegna
