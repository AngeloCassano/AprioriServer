package data;

import java.util.Iterator;

/**
 *La classe ContinuousAttributeIterator modella un iteratore che itera su di una sequenza di valori reali
 *equidistanti tra di loro, compresi in un intervallo [min,max] e ottenuti tramite discretizzazione. 
 */

public class ContinuousAttributeIterator implements Iterator<Float> {
	/**
	 * Valore minimo assunto dall'attributo continuo.
	 */
	private float min;
	
	/**
	 * Valore massimo assunto dall'attributo continuo . 
	 */
	private float max;
	
	/**
	 * Posizione dell'oggetto ContinuousAttribute nella sequenza.
	 */
	private int j=0;
	
	/**
	 * Numero di elementi della sequenza.
	 */
	private int numValues;
	
	/**
	 * 
	 * Istanzia un nuovo iteratore per attributi continui assegnandogli un intervallo e il numero di valori da 
	 * iterare.
	 * 
	 * @param min valore minimo dell'intervallo
	 * @param max valore massimo dell'intervallo
	 * @param numValues numero di valori reali della sequenza
	 */
	public ContinuousAttributeIterator(float min,float max,int numValues){
		this.min=min;
		this.max=max;
		this.numValues=numValues;
	}
	
	@Override
	/**
	 * Restituisce true se ci sono ancora valori reali successivi a quello in posizione j.
	 */
	public boolean hasNext() {
		return (j<=numValues);
	}

	@Override
	/**
	 * Restituisce un valore reale mediante l' algoritmo di discretizzazione dell'intervallo [min,max].
	 */
	public Float next() {
		j++;
		return min+((max-min)/numValues)*(j-1);
	}
}
