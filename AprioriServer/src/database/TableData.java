package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import database.TableSchema.Column;



/**
 * La classe TableData modella l'insieme delle tuple che formano una tabella presente su DB. 
 */
public class TableData {
	
	/**
	 * La inner class TupleData modella una singola tupla della tabella.
	 */

	public class TupleData {
		
		/** Lista che rappresenta la tupla di valori della tabella */
		public List<Object> tuple=new ArrayList<Object>();
		
		/**
		 * Restituisce una stringa che rappresenta la tupla attraverso la concatenazione dei suoi valori.
		 * @return stringa che rappresenta la tupla  
		 */
		@Override
		public String toString(){
			String value="";
			Iterator<Object> it= tuple.iterator();
			while(it.hasNext())
				value+= (it.next().toString() +" ");
			
			return value;
		}
	}
	
	/**
	 * Oggetto che permette l'accesso al database per poter effettuare delle query sql
	 */
	private DbAccess db;
	
	/**
	 * Crea una nuovo accesso al DB passandogli un oggetto di tipo DbAccess.
	 *
	 * @param db oggetto che rappresenta l'accesso al database 
	 */
	public void DbAccess(DbAccess db){
		this.db=db;
	}
	
	/**
	 * Estrae le tuple della tabella che soddisfano la query sql.
	 *
	 * @param table tabella da cui estrarre le tuple 
	 * @return lista delle tuple che soddisfano la query 
	 * @throws SQLException sollevata in caso di errore durante l'esecuzione della query
	 */
	public List<TupleData> getTransazioni(String table) throws SQLException{
		LinkedList<TupleData> transSet = new LinkedList<TupleData>();
		Statement statement;
		TableSchema tSchema=new TableSchema(db,table);
		
		
		String query="select ";
		
		for(int i=0;i<tSchema.getNumberOfAttributes();i++){
			Column c=tSchema.getColumn(i);
			if(i>0)
				query+=",";
			query += c.getColumnName();
		}
		if(tSchema.getNumberOfAttributes()==0)
			throw new SQLException();
		query += (" FROM "+table);
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			TupleData currentTuple=new TupleData();
			for(int i=0;i<tSchema.getNumberOfAttributes();i++)
				if(tSchema.getColumn(i).isNumber())
					currentTuple.tuple.add(rs.getFloat(i+1));
				else
					currentTuple.tuple.add(rs.getString(i+1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();

		
		
		return transSet;

	}

	/**
	 * Restituice il valore minimo o massimo di una colonna della tabella mediante un query sql 
	 * con funzione di aggregazione min o max. 
	 *
	 * @param table nome della tabella su cui effettuare la query 
	 * @param column colonna della tabella di cui calcolare il valore massimo o minimo
	 * @param aggregate valore di aggregazione min/max usato nella query 
	 * @return valore restituito dalla query di aggregazione min/max sulla colonna 
	 * @throws SQLException sollevata in caso di errore durante l'esecuzione della query 
	 * @throws NoValueException sollevata in caso di assenza di un valore all'interno del resultset
	 */
	
	 public Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException{
		Statement statement;
		TableSchema tSchema=new TableSchema(db,table);
		Object value=null;
		String aggregateOp="";
		
		String query="select ";
		if(aggregate==QUERY_TYPE.MAX)
			aggregateOp+="max";
		else
			aggregateOp+="min";
		query+=aggregateOp+"("+column.getColumnName()+ ") FROM "+table;
		
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		if (rs.next()) {
				if(column.isNumber())
					value=rs.getFloat(1);
				else
					value=rs.getString(1);
			
		}
		rs.close();
		statement.close();
		if(value==null)
			throw new NoValueException("No " + aggregateOp+ " on "+ column.getColumnName());
		return value;
	}
	
	/**
	 * Restituisce la lista di valori distinti in una colonna della tabella tramite query sql.
	 *
	 * @param table nome della tabella su cui effettuare la query 
	 * @param column colonna della tabella da cui ricavare i valori distinti 
	 * @return lista di valori distinti della colonna restituiti dalla query 
	 * @throws SQLException sollevata in caso di errore durante l'esecuzione della query 
	 */
	
	public List<Object> getDistinctColumnValues (String table, Column column) throws SQLException {
		
		String query ="select distinct "+column.getColumnName()+" FROM "+table;
		Statement statement = db.getConnection().createStatement();
		
		ResultSet rs = statement.executeQuery(query);
		List<Object> output = new LinkedList<Object>();
		if(column.isNumber()){
			while(rs.next()){
			output.add(rs.getFloat(column.getColumnName()));
		}
		}else{
			while(rs.next()){
				output.add(rs.getString(column.getColumnName()));
			}
		}
			
		rs.close();
		statement.close();

		return output;
	}
}
