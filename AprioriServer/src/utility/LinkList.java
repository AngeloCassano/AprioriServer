package utility;

import java.awt.List;

/**
 * La classe LinkList modella un lista collegata di oggetti.
 */
public class LinkList extends List {
	

	/**
	 * Puntatore di inizio lista
	 */

	private Puntatore inizioLista = null;

		
		
	/**
	 * Verifica se la lista è vuota.
	 *
	 * @return true se la lista è vuota, false altrimenti
	 */
	public boolean isEmpty() {
			return inizioLista == null;
	}
		
	/**
	 * Restituisce un puntatore a null.
	 *
	 * @return il puntatore a null
	 */
	public Puntatore firstList() {
			return null;
			
		}	

	/**
	 * Verifica se l'elemento puntato da p è l'ultimo della lista.
	 *
	 * @param p puntatore all'elemento della lista da veficare
	 * @return true se la lista è vuota o se l'elemento puntato da p è l'ultimo; false altrimenti
	 */
	public boolean endList(Puntatore p) {
			if (isEmpty()) return true;
			if (p == firstList())
				return inizioLista == null; // verifica che la lista sia vuota
			else
				return ((Puntatore)p).link.successivo == null; //verifica che elemento successivo a quello in posizione p sia nullo
		}


		/**
		 * Restituisce l'elemento puntato da p nella lista collegata.
		 *
		 * @param p puntatore dell'elemento da restituire
		 * @return elemento della lista puntato da p
		 */
		public Object readList(Puntatore p) {
			if (isEmpty())
				throw new IndexOutOfBoundsException("Lista vuota");
			if (p == firstList())
				return inizioLista.link.elemento;
			else
				return ((Puntatore) p).link.successivo.link.elemento;
			
				

		}
		
		/**
		 * Aggiunge l'elemento e alla lista.
		 *
		 * @param e elemento da aggiungere alla lista
		 */
		public void add(Object e) { //aggiunge in testa
			Puntatore temp;

			if (!isEmpty()) {
					temp = inizioLista;
					inizioLista = new Puntatore(new Cella(e));
					inizioLista.link.successivo = temp;
				}
			 else {
				// se la lista è vuota
				inizioLista = new Puntatore(new Cella(e));
			}

		}

		
		/**
		 * Restituisce il puntatore successivo all'elemento puntato da p.
		 * Nel caso in cui l'elemento puntato da p sia l'ultimo elemento della lista viene
		 * sollevata un'eccezione di tipo IndexOutOfBoundsException.
		 * La stessa eccezione è sollevata se la lista risulta vuota.
		 *
		 * @param p puntatore all' elemento di cui restituire il puntatore al successivo 
		 * @return puntatore all'elemento successivo di quello puntato da p
		 */
		public Puntatore succ(Puntatore p) {
			if (endList(p))
				throw new IndexOutOfBoundsException(
						"Posizione fine lista non valida");
			if (isEmpty())
				throw new IndexOutOfBoundsException("Lista vuota");
			if (p == firstList())
				return inizioLista;			
			else if (p == inizioLista)
				return inizioLista.link.successivo;
			else
				return ((Puntatore) p).link.successivo;
		}

	
}


