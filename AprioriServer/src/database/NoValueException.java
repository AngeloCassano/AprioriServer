package database;


/**
 * La classe NoValueException modella l'eccezione che si verifica in caso di assenza di un valore 
 * all'interno del resultset.
 */
public class NoValueException extends Exception {
	
	/**
	 * Stampa a video la stringa che descrive l'eccezione passata come parametro.
	 *
	 * @param str stringa da stampare a video
	 */
	 NoValueException(String str){
		System.out.println(str);
	}
}
