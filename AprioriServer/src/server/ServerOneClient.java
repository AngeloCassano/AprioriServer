package server;

import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mining.AssociationRule;
import mining.AssociationRuleMiner;
import mining.AssociationRuleArchive;
import mining.FrequentPattern;
import mining.FrequentPatternMiner;
import mining.OneLevelPatternException;

import data.Data;
import database.DatabaseConnectionException;
import database.NoValueException;

/**
 * La classe ServerOneClient soddisfa le richieste di un unico client tramite un thread dedicato.
 */
class ServerOneClient extends Thread {
	/**
	 * Socket che permette la comunicazione con il client.
	 */
	private Socket socket;
	
	/**
	 * Stream di ricezione di dati dal client.
	 */
	private ObjectInputStream in; 
	
	/**
	 * Stream per l'invio di dati al client.
	 */
	private ObjectOutputStream out;
	
	/**
	 * Archivio in cui memorizzare regole e pattern frequenti.
	 */
	private AssociationRuleArchive archive;
	
	/**
	 * Il costruttore istanzia un nuovo server per le richieste del client, ottenendo dalla socket
	 * passata come parametro uno stream per la ricezione dei dati dal client e uno per l'invio le risposte. 
	 * @param s socket di comunicazione con il client
	 * @throws IOException sollevata nel caso in cui si tenti di creare uno stream in mancanza di connessione
	 */
	ServerOneClient(Socket s) throws IOException {
		socket= s;
		
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in =new  ObjectInputStream(socket.getInputStream());
		start();
	
	}
	
	/**
	 * Il metodo run in base alla richiesta ricevuta dal client effettua una delle seguenti operazioni:
	 * -l'elaborazione sulla base di dati con algoritmo apriori, l'invio del risultato al client e la memorizzazione lato server;
	 * -l'acquisizione da file del risultato di una elaborazione precedentemente memorizzata sul server. 
	 * Al termine dell'esecuzione della richiesta la connessione tramite socket con il client è chiusa.
	 */
	
	@SuppressWarnings("finally")
	public void run() {
		System.out.println("Nuovo client connesso");
		
		try{
			while (true) {
				String output=null;
				int command=0;
				command = ((Integer)(in.readObject())).intValue();
				if(command==4){
						return;
				}
				switch(command)
				{
				case 1: // Learning a new archive from DB
					try{
						archive=new AssociationRuleArchive();
						String tableName=(String)in.readObject();
						Data trainingSet=new Data(tableName);
						Float minSup=(Float)in.readObject();
						Float minConf=(Float)in.readObject();
						LinkedList<FrequentPattern> outputFP=FrequentPatternMiner.frequentPatternDiscovery(trainingSet,minSup);
						Iterator<FrequentPattern> it=outputFP.iterator();
						while(it.hasNext()){
							FrequentPattern FP=it.next();
							archive.put(FP);
							LinkedList<AssociationRule> outputAR=null;
							try{
								outputAR = AssociationRuleMiner.confidentAssociationRuleDiscovery(trainingSet,FP,minConf);
								Iterator<AssociationRule> itRule=outputAR.iterator();
								while(itRule.hasNext()){
									archive.put(FP,itRule.next());
								}
								
							}
							
							catch(OneLevelPatternException e){
								
							}	
						}
						output=archive.toString();
					} 
					catch (DatabaseConnectionException e1) {
						// TODO Auto-generated catch block
						System.out.println(e1.getMessage());
					} 
					catch (SQLException e1) {
						// TODO Auto-generated catch block
						if(e1.getMessage().contains("Nome della tabella non esistente nel database")){
							output=e1.getMessage();
							return;
			
						}else{	
							System.out.println(e1.getMessage());
						}
					} 
					catch (NoValueException e1) {
						// TODO Auto-generated catch block
						System.out.println(e1.getMessage());
					}
					finally{
						out.writeObject(output);
						if(output.contains("Nome della tabella non esistente")){
							return;
						}
						break;
					}
				case 2: // SAVING ON File
					// TO DO
					if (archive==null) {
						System.out.println("Impossibile salvare");
					}
					out.writeObject("Nome file per backup su Server:");
					
					try{	
						archive.salva((String)in.readObject());
					}catch(FileNotFoundException e) {
						e.printStackTrace();
					}finally{
						out.writeObject("Pattern e regole salvate con SUCCESSO");
						break;
					}
					
					
				case 3: // STORING PATTERNS and RULES stored from a FILE
					try {
						String nomeFile=(String)in.readObject();
						archive= AssociationRuleArchive.carica(nomeFile);
						out.writeObject(archive.toString());
					} catch (FileNotFoundException e) {
						out.writeObject("File non trovato.");
					}
					finally {
						break;
					}
				default:
					System.out.println("COMANDO INESISTENTE");
					out.writeObject("COMANDO INESISTENTE");
				}
							
				}// END SWITCH
			
		}
		catch(SocketException e){
			return;
		} 
		catch(FileNotFoundException e ){
			try {
				out.writeObject("File non trovato");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} 
		catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
			 e.printStackTrace();
				
			}
	
		finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println("Socket non chiuso!");
			}
		}
	}
}



