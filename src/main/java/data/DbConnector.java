package data;

import java.sql.*;

public class DbConnector {
	
	private static DbConnector instancia;
	
	/* Connection String Parameters */
	private String driver="com.mysql.cj.jdbc.Driver";
	private String host="localhost";
	private String port="3306";
	private String user="root";
	private String password="root";
	private String db="java_hotel";
	
	/* Connection counter */
	private int conectados=0;
	
	/* A 'Connection' (session, java.sql class) with a specific database. SQL statements are executed and 
	 * results are returned within the context of a connection. 
	 * A Connection object's database is able to provide information describing its tables, 
	 * its supported SQL grammar, its stored procedures, the capabilities of this connection, and so on. 
	 * This information is obtained with the getMetaData method. */
	private Connection conn=null;
	
	/* Constructor */
	private DbConnector() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static DbConnector getInstancia() {
		if (instancia == null) {
			instancia = new DbConnector();
		}
		return instancia;
	}
	
	public Connection getConn() {
		try {
			if(conn==null || conn.isClosed()) {
				conn=DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+db, user, password);
				conectados=0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conectados++;
		return conn;
	}
	
	public void releaseConn() {
		conectados--;
		try {
			if (conectados<=0) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
