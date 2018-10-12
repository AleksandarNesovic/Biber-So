package com.nesovic.Telnet.DAO;

import java.sql.Connection;

import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnector{

	private static HikariConfig config = new HikariConfig("/home/dev33/git/BiberSo/biberSo/src/main/resources/db.properties");
	private static HikariDataSource ds=new HikariDataSource(config);

	private DatabaseConnector() {

	}
	public static Connection connect() throws ClassNotFoundException, SQLException {

		return ds.getConnection();
	}

}
