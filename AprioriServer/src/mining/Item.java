package mining;

import java.io.Serializable;

import data.Attribute;

/**
 * La classe astratta Item modella un item tramite la coppia attributo-valore.
 *
 */
abstract class Item implements Serializable{
	/**
	 * Attributo dell'item.
	 */
	private Attribute attribute;
	/**
	 * Valore assunto dall'attributo dell'item.
	 */
	private Object value;
	
	/**
	 * Istanzia un nuovo item assegnandogli un attributo e il suo valore.
	 * 
	 * @param a attributo da assegnare
	 * @param v valore dell'attributo da assegnare
	 */
	protected Item(Attribute a, Object v){
		attribute = a;
		value= v;
	}
	
	/**
	 * Restituisce l'attributo dell'item.
	 * @return attributo dell'item
	 */
	Attribute getAttribute(){
		return attribute;
	}
	
	/**
	 * Restituisce il valore dell'attributo dell'item.
	 * @return valore dell'attributo
	 */
	Object getValue(){
		return value;
	}
	
	/**
	 * Metodo astratto che effettua un confronto tra il valore dell'attributo di item e il valore
	 * v passato come parametro.
	 * @param v valore da confrontare 
	 * @return true se l'esito del confronto è positivo, false altrimenti 
	 */
	abstract boolean checkItemCondition(Object v);
	
	/**
	 * Restituisce una stringa che rappresenta l'item.
	 * @return stringa che rappresenta l'item.
	 */
	public String toString(){
		String s= attribute+"="+value;
		return s;
	}
	
}
