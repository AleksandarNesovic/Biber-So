package com.nesovic.Telnet.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	
	private static String driver="org.postgresql.Driver";
	private static String url="jdbc:postgresql://localhost:5432/OrdersDB";
	private static String username="postgres";
	private static String password="qqq";
	
	public static Connection connect() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(url, username, password);
		return conn;
	}
}
