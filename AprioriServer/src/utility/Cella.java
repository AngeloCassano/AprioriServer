package utility;
/**
 * La classe Cella modella un'unità di una lista collegata.
 */
 class Cella {
	/**
	 * Valore della cella della lista.
	 */
	Object elemento;
	
	/**
	 * Puntatore alla cella successiva della lista.
	 */
	Puntatore successivo=null; 

	/**
	 * Istanzia una nuova cella assegnandole un valore.
	 *
	 * @param e valore da assegnare alla cella
	 */
	public Cella(Object e){
		elemento = e;
	}

}
	