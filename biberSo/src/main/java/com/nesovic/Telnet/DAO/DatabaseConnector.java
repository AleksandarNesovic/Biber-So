package com.nesovic.Telnet.DAO;

import java.sql.Connection;

import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnector{

//	private static HikariConfig config = new HikariConfig("/home/dev33/git/BiberSo/biberSo/src/main/resources/application.properties");
//	private static HikariDataSource ds=new HikariDataSource(config);

	private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
 
    static {
    	config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl( "jdbc:postgresql://ec2-54-228-219-2.eu-west-1.compute.amazonaws.com:5432/d1s66pun62hp07?sslmode=require" );
        config.setUsername( "uoylbqzjvgiser" );
        config.setPassword( "c7ad7354a8c7bdaba39348302181886829e41b08e3fbd1d0d48c2777ccc0b6ba" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }
 
	private DatabaseConnector() {

	}
	public static Connection connect() throws ClassNotFoundException, SQLException {

		return ds.getConnection();
	}

}
