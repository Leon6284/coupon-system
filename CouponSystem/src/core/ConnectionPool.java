package core;

//import java.io.BufferedReader;
//import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConnectionPool {
	private Set<Connection> connections;
	private static ConnectionPool instance;
	private final int CONNECTIONS_NUM=10; //maximum number of connections
	
	/**
	 * Singleton constructor.
	 * Creates a set of connections to the database.
	 * @throws SQLException if a database error occurs or URL in the file is incorrect
	 * @throws FileNotFoundException if the file ConnectionURL does not exists
	 * @throws IOException if an I/O error occurs
	 */
	private ConnectionPool() throws SQLException, FileNotFoundException, IOException {
		connections = new HashSet<>();
		
//		File file = new File("files/ConnectionURL.txt");
		
		try//open file with database URL
//				BufferedReader reader = new BufferedReader(new FileReader(file));
				 {
			// load driver to memory
			String driverName="org.apache.derby.jdbc.ClientDriver";
			try {
				Class.forName(driverName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace(); // will not happen
			}
			
//			String url = reader.readLine();
			String url = "jdbc:derby://localhost:1527/couponDB";
			
			// adding connections to the pool
			for (int i = 0; i < CONNECTIONS_NUM; i++) {
				connections.add(DriverManager.getConnection(url));
			}
//		} catch (FileNotFoundException e) {
//			throw e;
//		} catch (IOException e) {
//			throw e;
		} catch (SQLException e) {
			throw e;
		}
		
	}
	
	/**
	 * Returns the singleton object
	 * @throws SQLException if a database error occurs or URL in the file is incorrect
	 * @throws FileNotFoundException if the file ConnectionURL does not exist
	 * @throws IOException if an I/O error occurs
	 * */
	public static ConnectionPool getInstance() throws SQLException, FileNotFoundException, IOException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	
	
	/**
	 * The method returns an available connection to the database.
	 * */
	public synchronized Connection getConnection() {
		
		// waiting for an available connection
		while (connections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// taking any connection from the pool
		Iterator<Connection> it = connections.iterator();
		Connection con = it.next();
		
		//remove the found connection and return it
		it.remove();
		return con;
	}
	
	/**
	 * The method returns a connection to the pool of connections.
	 * */
	public synchronized void returnConnection(Connection con) {
		connections.add(con);
		notify();
	}
	
	/**
	 * The method closes all connections in the pool and terminates the system.
	 * @throws SQLException if a database error occurs
	 * */
	public synchronized void closeAllConnections() throws SQLException {
		Iterator<Connection> it = connections.iterator();
		while (it.hasNext()) {
			try {
				it.next().close();
			} catch (SQLException e) {
				throw e;
			}
		}
		
	}
}
