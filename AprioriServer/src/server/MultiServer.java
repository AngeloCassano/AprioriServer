package server;

import java.io.IOException;
import java.net.*;

/**
 * La classe MultiServer modella un server che crea un nuovo thread, attraverso l'istanziazione di 
 * un oggetto della classe ServerOneClient per ogni richiesta accettata da parte di un generico client e
 * si occuperà di soddisfare le sue richieste. 
 * Il MultiServer potrà disporre di un numero di porta predefinita con cui interfacciarsi con l'esterno.
 */
public class MultiServer {
	
	/**
	 * Numero di porta assegnato al MultiServer.
	 */
	
	private static final int PORT = 8080;
	
	/**
	 * Il main istanzia un nuovo oggetto della classe MultiServer.
	 *
	 * @param args argomenti del main
	 */
	public static void main(String[] args){
		MultiServer Ms = new MultiServer(); 
	}
	
	/**
	 * Costruttore di MultiServer che esegue il thread che soddisferà la richiesta del client.
	 */
	public MultiServer(){
		run();
	}
	/**
	 * Il metodo run del thread  assegna una socket di connessione con un client 
	 * ad un oggetto della classe ServerOneClient che si occuperà di soddisfarne la richiesta.
	 */
	private void run(){
		ServerSocket s= null;
		try{ 
			s= new ServerSocket(PORT);
			while(true) {
				Socket socket = s.accept();
				try{
					new ServerOneClient(socket);
				}catch(IOException e){
					socket.close();
				}
			}
		}catch(IOException e){
		e.printStackTrace();
		}finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
