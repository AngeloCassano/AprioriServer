package data;

import java.io.Serializable;
import java.util.Iterator;

/**
 * La classe ContinuousAttribute modella un attributo continuo di una tabella nell'intervallo [min,max].
 * 
 */

public class ContinuousAttribute extends Attribute implements Iterable<Float>,Serializable{
	
	/**
	 * Valore massimo dell'intervallo.
	 */
	private float max;
	
	/**
	 * Valore minimo dell'intervallo.
	 */
	private float min;
	
	/**
	 * Inizializza i campi min, max e, richiama il costruttore della superclasse Attribute inizializzandone 
	 * i campi name ed index.   
	 * 
	 * @param n nome dell'attributo 
	 * @param i indice dell'attributo 
	 * @param min valore minimo dell'attributo 
	 * @param max valore massimo dell'attributo
	 */
	ContinuousAttribute(String n, int i, float min, float max){
		super(n,i);
		this.min=min;
		this.max=max;
		
	}
	
	/**
	 * Restituisce il valore minimo dell'attributo nell'intervallo [min,max]
	 * @return valore minimo dell'attributo nell'intervallo [min,max]
	 */
	float getMin(){return this.min;}
	
	/**
	 * Restituisce il valore massimo dell'attributo nell'intervallo [min,max]
	 * @return valore massimo dell'attributo nell'intervallo [min,max]
	 */
	float getMax(){return this.max;}

	/**
	 * restituisce l'iteratore per oggetti di tipo ContinuousAttribute
	 * @return iteratore per oggetti di tipo ContinuousAttribute
	 */
	@Override
	public Iterator<Float> iterator() {
		return new ContinuousAttributeIterator(min,max,5);
	}
	
	

	
}
