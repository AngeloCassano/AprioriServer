package mining;

import java.io.Serializable;

/**
 * La classe AssociationRule modella una regola di associazione confidente composta da un antecedente e un conseguente.
 */
public class AssociationRule implements Comparable<AssociationRule>,Serializable {
	/**
	 * Vettore di Item che rappresenta  l'insieme degli elementi antecedenti della regola.
	 */
	private Item antecedent[] = new Item[0];// alloco vettore di item vuoto
	/**
	 * Vettore di Item che rappresenta l'insieme degli elementi conseguenti della regola.
	 */
	private Item consequent[] = new Item[0];// come sopra
	/**
	 * Valore del supporto della regola. 
	 */
	private float support;
	/**
	 * Valore di confidenza della regola.
	 */
	private float confidence;
	
	/**
	 * Costruttore di AssociationRule che assegna il valore di supporto della regola passato come parametro. 
	 * @param s valore di supporto da assegnare.
	 */
	AssociationRule(float s){
		support=s;
	}
	/**
	 * Restituisce il valore di confidenza della regola.
	 * @return valore di confidenza.
	 */
	float getConfidence(){
		return confidence;
	}
	/**
	 * Restituisce il numero di elementi che compongono il conseguente.
	 * @return numero di elementi di consequent
	 */
	int getConsequentLength(){
		return consequent.length;
	}
	
	/**
	 * Restituisce il numero di elementi che compongono l'antecedente. 
	 * @return numero di elementi di antecedent.
	 */
	int getAntecedentLength(){
		return antecedent.length;
	}
	/**
	 * Aggiunge un nuovo item al vettore antecedent.
	 * @param i item da aggiungere.
	 */
	void addAntecedentItem(Item i){
		Item temp[]= new Item[antecedent.length+1];
		System.arraycopy(antecedent, 0, temp, 0, antecedent.length);
		temp [antecedent.length]= i;
		antecedent= temp;
	}
	
	/**
	 * Aggiunge un nuovo item al vettore consequent.
	 * @param i item da aggiungere.
	 */
	void addConsequentItem(Item i){
		Item temp[]= new Item[consequent.length+1];
		System.arraycopy(consequent, 0, temp, 0, consequent.length);
		temp [consequent.length]= i;
		consequent= temp;
	}
	
	/**
	 * Restituisce l'item del vettore antecedent in posizione index.
	 * @param index posizione dell'item da restituire.
	 * @return item del vettore in posizione index.
	 */
	Item getAntecedentItem(int index){
		return antecedent[index];
	}
	
	/**
	 * Restituisce l'item del vettore consequent in posizione index.
	 * @param index posizione dell'item da restituire.
	 * @return item del vettore in posizione index.
	 */
	Item getConsequentItem(int index){
		return consequent[index];
	}
	
	/**
	 * Assegna il valore di confidenza della regola passato come parametro. 
	 * @param c valore di confidenza da assegnare.
	 */
	void setConfidence(float c){
		confidence= c;
	}
	
	/**
	 * Restituisce una stringa che rappresenta la regola di associazione.
	 * @return stringa che rappresenta un oggetto di tipo AssociationRule 
	 */
	public String toString(){
		String temp= "";
		for(int i=0; i<antecedent.length; i++){
			temp+="("+antecedent[i]+")";
		}
		temp+="==>";
		for(int i=0; i<consequent.length; i++){
			temp+="("+consequent[i]+")";
		}
		temp+="["+support+","+confidence+"]" ;
		return temp;
	}
	
	/**
	 * Il metodo compareTo confronta il valore di confidenza dell'oggetto corrente con quello della regola di 
	 * associazione (o) passata come parametro e restituisce:
	 * a) 1 se la confidena è maggiore
	 * b) -1 se la confidenza è minore
	 * c) 0 se sono uguali
	 * @return risultato del confronto
	 */
	@Override
	public int compareTo(AssociationRule o) {
		
		if(this.getConfidence()>o.getConfidence())
			return 1;
		else if(this.getConfidence()==o.getConfidence())
			return 0;
		else return -1;
		
	}
	
	
	
}
