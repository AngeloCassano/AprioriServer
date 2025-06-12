package data;

/**
 * Modella l'eccezione che si verifica quando l'insieme delle transazioni è vuoto. 
 */
public class EmptySetException extends Exception{
 
	@Override
	/**
	 *Restituisce la stringa contenente il messaggio di errore relativo all'eccezione.
	 *@return stringa che descrive l'eccezione verificatasi
	 */

	public String getMessage(){
	 return "insieme delle transazioni vuoto";
	}
}
