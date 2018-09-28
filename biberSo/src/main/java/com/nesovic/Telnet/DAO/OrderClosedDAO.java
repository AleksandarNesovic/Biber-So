package com.nesovic.Telnet.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderClosedDAO {
	private Connection conn=null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public OrderClosedDAO() {
		super();
	}
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void insertOrderClosed(String date,boolean status) {
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into orderclosed(order_date,status) values (?,?)");
			preparedStatement.setString(1, date);
			preparedStatement.setBoolean(2, status);
			preparedStatement.execute();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
	}
	public void updateOrderClosed(String date,boolean status) {
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("update orderclosed set status=? where order_date=?");
			preparedStatement.setBoolean(1, status);
			preparedStatement.setString(2, date);
			preparedStatement.execute();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
	}
	
	public boolean checkOrderClosed(String date) {
		boolean status=false;
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select status from orderclosed where order_date=?");
			preparedStatement.setString(1, date);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				status=resultSet.getBoolean(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return status;
	}
	
	
}
