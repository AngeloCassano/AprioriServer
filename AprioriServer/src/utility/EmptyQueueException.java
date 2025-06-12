package utility;


/**
 * La classe EmptyQueueException modella l'eccezione che si verifica quando si tenta di effettuare un'operazione
 * non consentita su di una coda vuota.
 */
public class EmptyQueueException extends Exception{
	
	/**
	 * Restituisce un messaggio che descrive l'eccezione che si è verificata.
	 *
	 * @return messaggio che descrive l'eccezione
	 */
	public String getmessage(){
		return"operazione non valida su una coda vuota";
	}
}
