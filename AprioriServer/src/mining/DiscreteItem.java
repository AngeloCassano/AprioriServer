package mining;

import java.io.Serializable;

import data.DiscreteAttribute;

/**
 * La classe DiscreteItem modella una coppia attributo discreto-valore(DiscreteAttribute,String).
 * 
 * {@link DiscreteAttribute}
 *  
 */
public class DiscreteItem extends Item implements Serializable{
	
	/**
	 * Istanzia un nuovo DiscreteItem richiamando il costruttore della superclasse.
	 *
	 * @param attribute attributo discreto dell'item da assegnare.
	 * @param value valore dell'item da assegnare.
	 */
	public DiscreteItem(DiscreteAttribute attribute, String value){
		super(attribute, value);
	}

	/**
	 * Confronta il valore value dell'item con quello di v.
	 * @param v valore da confrontare.
	 * @return true se i valori coincidono, false altrimenti.
	 */
	boolean checkItemCondition(Object v){
		String b =(String) v;
		boolean result= b.equals(this.getValue());
		
		return result;
	}
	
	
}
