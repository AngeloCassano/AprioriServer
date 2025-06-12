package mining;


/**
 * La classe OneLevelPatternException modella l'eccezione che si verifica nel caso in cui si tenti di 
 * generare una regola confidente a partire da un pattern di lunghezza 1. 
 */
public class OneLevelPatternException extends Exception {
	
	/**
	 * Pattern frequente di lunghezza 1 su cui si è verificata l'eccezione, da visualizzare.
	 */
	FrequentPattern fponelevel;
	
	/**
	 * Istanzia una nuova OneLevelPatternException assegnandogli il pattern frequente di lunghezza 1 passato 
	 * come parametro.
	 *
	 * @param fp frequent pattern di lunghezza 1 da assegnare
	 */
	public OneLevelPatternException(FrequentPattern fp){
		fponelevel= fp;
		
	}
	/**
	 * Restituisce una stringa che descrive l'eccezione che si è verificata, visualizzando anche il pattern
	 * frequente che l'ha generata.
	 * @return la stringa che descrive l'eccezione
	 */
	public String getMessage(){
		return "Length of "+fponelevel.toString()+" is 1";
	}
}
