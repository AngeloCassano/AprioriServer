package data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * La classe DiscreteAttribute modella un attributo discreto rappresentato tramite un insieme 
 * di valori distinti appartenenti al dominio dell'attributo.
 */
public class DiscreteAttribute extends Attribute implements Serializable {
	/**
	 * Lista di valori dell'attributo discreto. 
	 */
	private ArrayList<String> values ;
	
	/**
	 * Istanzia un nuovo attributo discreto assegnandogli una lista di valori e
	 * richiamando il costruttore della superclasse gli assegna un nome e un indice della tabella.
	 * 
	 * @param name nome dell'attributo discreto da assegnare
	 * @param index indice della tabella da assegnare
	 * @param values lista di valori da assegnare
	 */
	DiscreteAttribute(String name, int index, ArrayList<String> values){
		super(name,index);
		this.values= values;
		
	}
	
	/**
	 * Restituisce il numero degli elementi della lista dei valori dell'attributo discreto.
	 *
	 * @return numero degli elementi della lista dei valori dell'attributo discreto
	 */
	public int getNumberOfDistinctValues(){return values.size();}
	 
 	/**
 	 * Restituisce il valore in posizione index nella lista dei valori dell'attributo discreto.
 	 *
 	 * @param index posizione nella lista del valore da restituire
 	 * @return valore in posizione index nella lista
 	 */
 	 public String getValue(int index){
		String val=this.values.get(index);
		return val;
	}
	
}