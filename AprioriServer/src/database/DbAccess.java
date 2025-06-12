package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.management.InstanceNotFoundException;


/**
 * La classe DbAccess contiene le informazioni necessarie alla gestione dell'accesso al DB 
 * per la lettura dei dati di training.
 * 
 */
public class DbAccess {
	
	/**
	 * Nome del driver MySql
	 */
	private static final String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	
	/**
	 * Tipo del DBMS
	 */
	private static final String DBMS = "jdbc:mysql";
	
	/**
	 * Nome del server a cui connettersi
	 */
	private  final String SERVER = "localhost";
	
	/**
	 * numero di porta su cui il DBMS accetta connessioni
	 */
	private  int PORT = 3306;
	
	/**
	 * nome del database a cui connettersi
	 */
	private  String DATABASE = "AprioriDB";
	
	/**
	 * id dell'utente
	 */
	private  String USER_ID = "AprioriUser";
	
	/**
	 * password dell'utente
	 */
	private  String PASSWORD = "apriori";
	
	/**
	 * oggetto che rappresenta una connessione al database
	 */
	private Connection conn;

	/**
	 * Effettua la connessione al DB verificando che le informazioni per la connessione siano corrette.
	 * @throws DatabaseConnectionException Eccezione lanciata in caso di mancata connessione al database 
	 */
	public  void initConnection() throws DatabaseConnectionException{
		String connectionString = DBMS+"://" + SERVER + ":" + PORT + "/" + DATABASE;
		try {
			
				Class.forName(DRIVER_CLASS_NAME).newInstance();
			} 
		catch (IllegalAccessException e) {
				
				e.printStackTrace();
				throw new DatabaseConnectionException(e.toString());
			}
		catch (InstantiationException e) {
					
					e.printStackTrace();
					throw new DatabaseConnectionException(e.toString());
			} 
		catch (ClassNotFoundException e) {
			System.out.println("Impossibile trovare il Driver: " + DRIVER_CLASS_NAME);
			throw new DatabaseConnectionException(e.toString());
		}
		
		try {
			conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);
			
		} catch (SQLException e) {
			System.out.println("Impossibile connettersi al DB");
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}
		
		
	}
	
	/**
	 * Restituisce un oggetto che rappresenta la connessione con il DB.
	 *
	 * @return la connessione col database
	 */
	public Connection getConnection(){
		return conn;
	}
	
	/**
	 * Chiude la connessione con il DB.
	 */
	public void closeConnection(){
		try{
		conn.close();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
