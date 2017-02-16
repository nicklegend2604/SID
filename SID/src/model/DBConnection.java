package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static Connection  connection = null;
	private DBConnection(){}

	 public static Connection getConnectionInstance() throws SQLException {
	        if (connection == null){
					connection =	DriverManager.getConnection("jdbc:mariadb://localhost:3306/sid", "root", "");
	        }
	        return connection;
	 }

}
