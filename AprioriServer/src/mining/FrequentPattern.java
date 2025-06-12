package mining;

import java.io.Serializable;
import java.util.*;

/**
 * La classe FrequentPattern modella un pattern frequente all'interno delle transazioni di una tabella 
 * in un database.
 */
public class FrequentPattern implements Serializable{
	/**
	 * Lista di Item che rappresenta il FrequentPattern.
	 */
	private List<Item> fp;
	/**
	 * Valore di supporto calcolato per il pattern frequente.
	 */
	private float support;
	
	/**
	 * Istanzia un nuovo pattern frequente assegnandogli una lista collegata vuota.
	 */
	public FrequentPattern(){
		fp= new LinkedList<Item>();
	}
	/**
	 * Aggiunge un nuovo item alla lista del frequent pattern.
	 * @param item item da aggiungere alla lista del frequent pattern.
	 */
	void addItem(Item item)
	{
		List<Item> temp =new LinkedList<Item>();
		temp.addAll(fp);
		temp.add(item);
		fp=temp;	
	}
	/**
	 * Restituisce l'item che si trova in posizione index nella lista fp.
	 * @param index posizione dell'item da restituire
	 * @return item in posizione index
	 */
	Item getItem(int index){
		return fp.get(index);
	}
	/**
	 * Restituisce il valore del supporto calcolato sul pattern frequente.
	 * @return supporto del pattern frequente
	 */
	float getSupport(){
		return support;
	}
	/**
	 * Restituisce il numero degli elementi di fp.
	 * @return numero degli elementi di fp
	 */
	int getPatternLength(){
		return fp.size();
	}
	/**
	 * Assegna al frequent pattern il valore di supporto s.
	 * @param s supporto da assegnare al frequent pattern
	 */
	void setSupport(float s){
		support=s;
	}
	/**
	 * Restituisce una stringa che rappresenta un oggetto di tipo FrequentPattern.
	 * @return stringa che rappresenta l'oggetto FrequentPattern
	 */
	public String toString(){
		String value="";
		Iterator<Item> i = fp.iterator();
		while (i.hasNext()){
			value+=i.next();
			if(i.hasNext())	   value+=" AND ";
		}
		value+="["+support+"]";
		return value;
	}

	


	
}
