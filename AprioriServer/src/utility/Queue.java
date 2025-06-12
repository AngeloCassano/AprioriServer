package utility;



/**
 * La classe Queue modella la coda che conterrà i pattern frequenti da cui generare nuovi pattern tramite raffinamento.
 * 
 *
 * @param <T> tipo generico degli elementi della coda
 */
public class Queue<T> {
		/**
		 * Primo elemento della coda
		 */
		private Record begin = null;
		/**
		 * Ultimo elemento della coda
		 */
		private Record end = null;
		
		/**
		 *	La classe Record è Inner class di Queue e modella un singolo componente della coda
		 *	composto da un elemento di tipo generico T e il riferimento al suo successivo.
		 * 
		 *
		 */
		private class Record {
			/**
			 * Elemento della coda.
			 */
	 		private T elem;
	 		/**
	 		 * Riferimento all'elemento successivo.
	 		 */
	 		private Record next;
	 		
	 		/**
	 		 * Costruttore della classe Record che istanzia un componente della coda assegnandogli
	 		 * un elemento il parametro e insieme al riferimento al suo successivo il valore null.
	 		 * @param e elemento di tipo generico T da assegnare
	 		 */
			public Record(T e) {
				this.elem = e; 
				this.next = null;
			}
		}
		

		/**
		 * Verifica che la coda sia vuota.
		 *
		 * @return true se la coda è vuota, false altrimenti
		 */
		public boolean isEmpty() {
			return this.begin == null;
		}

		/**
		 * Aggiunge un elemento alla coda.
		 *
		 * @param e elemento da aggiungere
		 */
		public void enqueue(T e) {
			if (this.isEmpty())
				this.begin = this.end = new Record(e);
			else {
				this.end.next = new Record(e);
				this.end = this.end.next;
			}
		}


		/**
		 * Restituisce il primo elemento della coda. 
		 * Nel caso in cui la coda sia vuota verrà sollevata l'eccezione EmptyQueueException
		 * e visualizzato un messaggio di errore.
		 *
		 * @return il primo elemento
		 */
		public T first() {
			try{
				if(isEmpty()){
					throw new EmptyQueueException();
					
				}
				
			}
			catch (EmptyQueueException e) {
				System.out.println(e.getmessage());
			}
			return this.begin.elem;
		}

		/**
		 * Rimuove l'elemento in testa della coda.
		 * Nel caso in cui la coda sia vuota verrà sollevata l'eccezione EmptyQueueException
		 * e visualizzato un messaggio di errore.
		 */
		public void dequeue(){
			if(this.begin==this.end){
				try{
					if(this.begin==null) throw new EmptyQueueException();
				else  this.begin=this.end=null;	
				}
				catch(EmptyQueueException e){
					e.getmessage();
				}
			}
			
			else
				begin=begin.next;
			
			}

	}