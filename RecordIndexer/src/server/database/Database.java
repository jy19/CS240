package server.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Database {
private static Logger logger;
	
	static {
		logger = Logger.getLogger("database");
	}

	public static void initialize(){
		
		logger.entering("server.database.Database", "initialize");
		
		//Load the SQLite database driver
		try{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("Could not load SQLite database driver");
		}

		logger.exiting("server.database.Database", "initialize");
	}

	private UserDAO userDAO;
	private ProjectDAO projectDAO;
	private ImageDAO imageDAO;
	private FieldDAO fieldDAO;
	private RvalueDAO valueDAO;

	private Connection connection;
	
	public Database() {
		userDAO = new UserDAO(this);
		projectDAO = new ProjectDAO(this);
		imageDAO = new ImageDAO(this);
		fieldDAO = new FieldDAO(this);
		valueDAO = new RvalueDAO(this);
		connection = null;
	}
	
	
	public Connection getConnection() {
		return connection;
	}

	public void startTransaction() {

		logger.entering("server.database.Database", "startTransaction");
		
		//Open a connection to the database and start a transaction
		String dbName =  "Database" + File.separator + "Indexer.sqlite";
		String connectionURL = "jdbc:sqlite:" + dbName;
		try{
			//open a database connection
			connection = DriverManager.getConnection(connectionURL);
			//start a transaction
			connection.setAutoCommit(false);
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("Could not open database connection!");
		}
		
		logger.exiting("server.database.Database", "startTransaction");
	}
	
	public void endTransaction(boolean commit){
		
		logger.entering("server.database.Database", "endTransaction");
		
		//Commit or rollback the transaction and close the connection
		try {
			if(commit){
				connection.commit();
			}
			else{
				connection.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("could not end transaction");
		}
		finally{
			try{
				connection.close();
			}
			catch(SQLException e){
				e.printStackTrace();
				System.out.println("could not close??");
			}
		}
		
		connection = null;
		logger.exiting("server.database.Database", "endTransaction");
	}

	public ImageDAO getImageDAO() {
		return imageDAO;
	}


	public FieldDAO getFieldDAO() {
		return fieldDAO;
	}


	public RvalueDAO getValueDAO() {
		return valueDAO;
	}

	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	
	public ProjectDAO getProjectDAO() {
		return projectDAO;
	}

	
}
