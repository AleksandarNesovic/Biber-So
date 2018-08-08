package com.nesovic.Telnet.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	
	private static String driver="org.postgresql.Driver";
	private static String url="jdbc:postgresql://ec2-54-217-235-159.eu-west-1.compute.amazonaws.com:5432/d5utn7s4s8d8d2";
	private static String username="iqkhtgkxrvoogl";
	private static String password="ce9c09756b23f004f5fbf776c6e9f5c2cea270c957660abcebf60bfc9dce1b94";
	
	public static Connection connect() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn=DriverManager.getConnection(url, username, password);
		return conn;
	}
}
