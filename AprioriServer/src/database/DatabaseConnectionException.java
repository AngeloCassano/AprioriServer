package database;


/**
 * Modella l'eccezione che si verifica in caso di mancata connessione al database. 
 */
public class DatabaseConnectionException extends Exception {
	
	/**
	 * Stampa a video la stringa, che descrive l'eccezione, passata come parametro
	 *
	 * @param str stringa da stampare a video
	 */
	 DatabaseConnectionException(String str){
		System.out.println(str);
	}
}
