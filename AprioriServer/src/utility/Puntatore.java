package utility;

/**
 * La classe Puntatore modella un puntatore all'interno di una cella di una lista collegata
 * che fa riferimento alla cella successiva .
 */
public class Puntatore  {
	
		/** 
		 * Cella a cui il puntatore fa riferimento. 
		 * */
		public Cella link;
		
		/**
		 * Istanzia un nuovo puntatore assegnandogli la cella a cui fare riferimento.
		 *
		 * @param c cella da assegnare al puntatore
		 */
		public Puntatore(Cella c) {
			link = c;
		}
	}
