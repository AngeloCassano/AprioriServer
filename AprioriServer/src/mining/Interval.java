package mining;

import java.io.Serializable;

/**
 * La classe Interval modella l'intervallo a cui appartiene un attributo continuo della tabella.
 */
public class Interval implements Serializable{
	/**
	 * Estremo inferiore dell'intervallo a cui appartiene l'attributo continuo.
	 */
	private float inf; 
	
	/**
	 * Estremo superiore dell'intervallo a cui appartiene l'attributo continuo.
	 */
	private float sup;
	
	/**
	 * Istanzia un nuovo oggetto di tipo Interval assegnandogli un estremo inferiore e uno superiore.
	 *
	 * @param inf estremo inferiore da assegnare
	 * @param sup estremo superiore da assegnare
	 */
	public Interval (float inf, float sup){
		setInf(inf);
		setSup(sup);
	}
	
	/**
	 * Assegna al campo inf il valore passato come parametro.
	 *
	 * @param inf estremo inferiore da assegnare
	 */
	void setInf(float inf){
		this.inf=inf;
	}
	
	/**
	 * Assegna al campo sup il valore passato come parametro.
	 *
	 * @param sup estremo superiore da assegnare
	 */
	void setSup(float sup){
		this.sup=sup;
	}
	
	/**
	 * Restituisce l'estremo inferiore dell'intervallo.
	 *
	 * @return l'estremo inferiore 
	 */
	float getInf(){
		return inf;
	}
	
	/**
	 * Restituisce l'estremo superiore dell'intervallo.
	 *
	 * @return l'estremo superiore
	 */
	float getSup(){
		return sup;
	}
	
	/**
	 * Verifica che il valore passato come parametro appertanga all'intervallo [inf,sup).
	 *
	 * @param value valore di cui verificare l'appartenenza all'intervallo
	 * @return true se il valore appartiene all'intervallo, false altrimenti 
	 */
	boolean checkValueInclusion(float value){
		if(value>=inf && value<sup)return true;
		return false;
	}
	
	/**
	 * Restituisce una stringa che rappresenta l'intervallo.
	 * @return stringa che rappresenta l'intervallo
	 */
	public String toString(){
		return "["+inf+","+sup+"[";
	}
	
	
}
