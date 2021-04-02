package it.polito.tdp.corsi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
 static String jdbcURL="jdbc:mysql://localhost/iscritticorsi?user=root&password=root";
	 
	 public static Connection getConnection() throws SQLException {
		 return DriverManager.getConnection(jdbcURL);
	 }
}
