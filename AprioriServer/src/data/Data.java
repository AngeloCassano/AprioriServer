package data;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.NoValueException;
import database.QUERY_TYPE;
import database.TableData;
import database.TableData.TupleData;
import database.TableSchema;

/**
 * La classe Data modella la tabella memorizzata nel database sulla quale effettuare l'operazione di data-mining.
 */
public class Data {
	
	/**
	 * Matrice che implementa la tabella sulla quale elaborare i dati. 
	 */
	private Object data [][] ;
	
	/**
	 * Numero delle transazioni della tabella. 
	 */
	private int numberOfExamples;
	
	/**
	 * Lista contenente gli attributi che identificano le colonne della tabella.
	 */
	private List<Attribute> attributeSet=new LinkedList<Attribute>();
	
	/**
	 * Il costruttore si connette al database dba prelevando la tabella nomeTab e memorizzandola 
	 * nella matrice data.
	 * 
	 * @param nomeTab nome della tabella da cui prelevare i dati
	 * @throws DatabaseConnectionException sollevata in caso di mancata connessione al database 
	 * @throws SQLException sollevata in caso di errore nell'esecuzione di una query
	 * @throws NoValueException sollevata in caso di assenza di un valore all'interno del resultset
	 */
	public Data(String nomeTab) throws DatabaseConnectionException, SQLException, NoValueException{
		
		DbAccess dba= new DbAccess();
		
		dba.initConnection();
		TableSchema ts=null;
		try {
			ts = new TableSchema(dba,nomeTab);
			TableData td = new TableData();
			td.DbAccess(dba);
			for(int j=0; j<ts.getNumberOfAttributes(); j++ ){
				List<Object> distval=null;
					distval = td.getDistinctColumnValues(nomeTab,ts.getColumn(j));
					Iterator<Object> it1 = distval.iterator();
					if(ts.getColumn(j).isNumber()){
							ArrayList<Float> values = new ArrayList<Float>();
							while(it1.hasNext()){
								values.add((Float) it1.next());
							}

								attributeSet.add(new ContinuousAttribute (ts.getColumn(j).getColumnName(),j,(float)td.getAggregateColumnValue(nomeTab, ts.getColumn(j),QUERY_TYPE.MIN),(float)td.getAggregateColumnValue(nomeTab, ts.getColumn(j),QUERY_TYPE.MAX)));		
					}else{  
						ArrayList<String> values = new ArrayList<String>();
						while(it1.hasNext()){
							values.add((String)it1.next());
						}
						
							attributeSet.add(new DiscreteAttribute (ts.getColumn(j).getColumnName(),j,values));
						
						
					}
				
			}
			
		// riempimento celle tabella 
			List<TupleData>tuple= td.getTransazioni(nomeTab);
			numberOfExamples= tuple.size();
			data= new Object[numberOfExamples][ts.getNumberOfAttributes()];	
			for(int i=0; i<numberOfExamples; i++){
				Iterator<Object> it = tuple.get(i).tuple.iterator();
				for(int j=0; j<ts.getNumberOfAttributes(); j++){
					
					
					if(ts.getColumn(j).isNumber()){
						data[i][j]= (float) it.next();
					}
					else{
						data[i][j]= (String) it.next();
					}
				} 
			}	
		}
		
		finally {
			dba.closeConnection();
		}
		
	}	

	
	/**
	 * Restituisce il numero delle transazioni della tabella. 
	 *
	 * @return numero delle transazioni della tabella 
	 */
	public int getNumberOfExamples () {
		return this.numberOfExamples;
	}
	
	/**
	 * Restituisce il numero degli attributi della tabella 
	 *
	 * @return numero degli attributi della tabella
	 */
	public int getNumberOfAttributes(){ return attributeSet.size();}
	
	/**
	 * Restituisce l'attributo della tabella in posizione attributeIndex.
	 *
	 * @param attributeIndex posizione dell'attributo da resituire.
	 * @return attributo della tabella
	 */
	public Attribute getAttribute(int attributeIndex){ 
			
			return attributeSet.get(attributeIndex);
		
	}
	
	/**
	 * Restituisce il valore della tabella in riga exampleIndex e in colonna attributeIndex
	 *
	 * @param exampleIndex posizione della riga della tabella  
	 * @param attributeIndex posizione della colonna della tabella 
	 * @return valore della tabella in riga exampleIndex e in colonna attributeIndex
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data[exampleIndex][attributeIndex];
	}
	
	@Override
	/**
	 * Restituisce una stringa che rappresenta la tabella, ottenuta concatenandone i suoi valori.  
	 */
	public String toString(){
		String result="";
		String work="";
		
		for(int i=0 ; i<this.getNumberOfExamples(); i++){
			work="";
		for(int j = 0; j<this.getNumberOfAttributes(); j++){
			work+=data[i][j]+",";
		}
		result+=work+"/n";
		}
		return result;
	}
}
