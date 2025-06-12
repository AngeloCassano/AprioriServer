package mining;

/**
 * La classe NoPatternException modella l'eccezione che si verifica nel caso in cui un pattern frequente non generi
 * nessuna regola confidente.
 */
public class NoPatternException extends Exception {
	/**
	 * Restituisce una stringa che descrive l'eccezione verificatasi.
	 * @return stringa contenente la descrizione dell'eccezione 
	 */
	 public String getMessage(){
		return "nessuna regola confidente generata";
	}
}
