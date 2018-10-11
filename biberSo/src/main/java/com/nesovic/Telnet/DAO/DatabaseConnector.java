package com.nesovic.Telnet.DAO;

import java.sql.Connection;


import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnector{

//	private static final String driver="driver";
//	private static final String url="jdbcUrl";
//	private static final String username="username";
//	private static final String password="password";
//
//	private static Properties properties=null;
//	static {
//		    	try {
//		    		properties=new Properties();
//		    		properties.load(new FileInputStream("/src/main/resources/config"));
//		    		
//					config.setDriverClassName(properties.getProperty(driver));
//					config.setJdbcUrl( properties.getProperty(url) );
//					config.setUsername( properties.getProperty(username) );
//					config.setPassword( properties.getProperty(password) );
//					config.addDataSourceProperty( "cachePrepStmts" , "true" );
//					config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
//					config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
//					config.setMaximumPoolSize(25);
//					config.setMinimumIdle(0);
//					config.setIdleTimeout(30000);
//					config.setLeakDetectionThreshold(3000);
//					
//					ds = new HikariDataSource( config );
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//		    }

	
//	Context cxt = new InitialContext();
//	private HikariDataSource ds=(HikariDataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );	
		
		//private HikariConfig config = new HikariConfig();
	  

	   // private static String driver="org.postgresql.Driver";
//		private static String url="jdbc:postgresql://ec2-54-228-219-2.eu-west-1.compute.amazonaws.com:5432/d1s66pun62hp07?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
//		private static String username="uoylbqzjvgiser";
//		private static String password="c7ad7354a8c7bdaba39348302181886829e41b08e3fbd1d0d48c2777ccc0b6ba";
//		private static String url="jdbc:postgresql://localhost:5432/OrdersDB";
//		private static String username="postgres";
//		private static String password="qqq";
	//   static {
//	    	config.setDriverClassName(driver);
//	        config.setJdbcUrl( url );
//	        config.setUsername( username );
//	        config.setPassword( password );
//	        config.addDataSourceProperty( "cachePrepStmts" , "true" );
//	        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
//	        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
//	        config.setMaximumPoolSize(25);
//	        config.setMinimumIdle(0);
//	        config.setIdleTimeout(30000);
//	        config.setLeakDetectionThreshold(3000);
	        
	     //   ds = new HikariDataSource( config );
	 //   }

	private DatabaseConnector() {

	}
	public static Connection connect() throws ClassNotFoundException, SQLException, NamingException {
		
		Context cxt = new InitialContext();
		//HikariDataSource ds=InitialContext.doLookup( "java:/comp/env/jdbc/postgres" );
		//HikariDataSource ds=(HikariDataSource)cxt.lookup("java:/comp/env/jdbc/postgres");
		DataSource ds=(DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");
		/*	Class.forName(driver);
		Connection conn=DriverManager.getConnection(url, username, password);

		return conn;*/
		//if(ds==null) ds=new HikariDataSource(config);
		return ds.getConnection();
	}

}
