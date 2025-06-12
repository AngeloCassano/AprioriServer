package data;

import java.io.Serializable;

/**
 * La classe Attribute modella un attributo discreto o continuo di una tabella. 
 */
public abstract class Attribute implements Serializable{
	private String name= new String();
	private int index;
	
	/**
	 * Istanzia un nuovo attributo composto da una nome e un indice.
	 *
	 * @param n nome dell'attributo.
	 * @param i indice dell'attributo.
	 */
	protected Attribute(String n, int i) {
		this.name=n;
		this.index=i;
	}
	
	/**
	 * Restituisce il nome dell'attributo.
	 *
	 * @return nome dell'attributo.
	 */
	public String getName(){return this.name;}
	
	/**
	 * Restituisce l'indice dell'attributo.
	 * 
	 * @return indice dell'attributo.
	 */
	public int getIndex(){return this.index;}
	
	@Override
	/**
	 * Restituisce una stringa che rappresenta l'attributo.
	 */
	public String toString(){return this.name;}	
}
