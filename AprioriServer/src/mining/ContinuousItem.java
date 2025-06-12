package mining;
import java.io.Serializable;

import data.ContinuousAttribute;


/**
 * La classe ContinuousItem modella una coppia attributo continuo-intervallo (ContinuousAttribute,Interval).
 * {@link ContinuousAttribute}
 * {@link Item}
 * {@link Interval}
 */
public class ContinuousItem extends Item implements Serializable{
	
	/**
	 * Costruttore che richiama quello della super classe Item per inizializzare un nuovo oggetto di tipo ContinuousItem.
	 *
	 * @param attribute attributo continuo da assegnare.
	 * @param value valore dell'intervallo in cui è definito attribute.
	 */
	public ContinuousItem(ContinuousAttribute attribute, Interval value){
		super(attribute,value);
	}
	
	/**
	 * Controlla che il valore passato v sia appartenente all'intervallo.
	 * 
	 *@param v valore di cui verificare l'appartenenza all'intervallo.
	 *@return true se v appartiene all'intervallo, altrimenti false.
	 */
	boolean checkItemCondition(Object v) {
		 return  ((Interval) getValue()).checkValueInclusion((float)v);
		
	}
	/**
	 * Restituisce una stringa che rappresenta l'oggetto ContinuousItem.
	 * @return stringa che rappresenta l'oggetto ContinuousItem.
	 */
	public String toString(){
		return getAttribute()+" in ["+((Interval)getValue()).getInf()+","+((Interval)getValue()).getSup()+"[";
		
	}
	
	
}
