package com.nesovic.Telnet.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.nesovic.Telnet.model.Clients;
import com.nesovic.Telnet.model.Meal;
import com.nesovic.Telnet.model.Order;

public class OrdersDAO {
	
	private Connection conn=null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public OrdersDAO() {
		super();
	}
	
	public ArrayList<Order> selectOrders(){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select * from orders");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				item=new Order();
				item.setOrder_id(resultSet.getInt(1));
				
				Clients client=daoc.selectClientsById(resultSet.getInt(2));
				item.setClient(client);
				
				Meal glavno=daog.selectMealById(resultSet.getInt(3));
				item.setMeal(glavno);
				
				item.setQuantity(resultSet.getInt(4));
				item.setOrder_price(resultSet.getDouble(5));
				item.setOrder_date(resultSet.getString(6));
				
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public Order selectOrderById(int id){
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select * from orders where order_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			if(resultSet.next()) {
				item=new Order();
				item.setOrder_id(resultSet.getInt(1));
				
				Clients client=daoc.selectClientsById(resultSet.getInt(2));
				item.setClient(client);
				
				Meal glavno=daog.selectMealById(resultSet.getInt(3));
				item.setMeal(glavno);
				
				item.setQuantity(resultSet.getInt(4));
				item.setOrder_price(resultSet.getDouble(5));
				item.setOrder_date(resultSet.getString(6));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return item;
	}
	public Order insertOrder(Order item) {
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into orders(client_id,meal_id,quantity,order_price,order_date) values (?,?,?,?,?)");
			preparedStatement.setInt(1, item.getClient().getClient_id());
			preparedStatement.setInt(2, item.getMeal().getMeal_id());
			preparedStatement.setInt(3, item.getQuantity());
			preparedStatement.setDouble(4, ((item.getQuantity()/1000)*item.getMeal().getPrice()));
			preparedStatement.setDate(5, new Date(System.currentTimeMillis()));
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return item;
	}
	public Order updateOrder(Order item) {
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("update orders set client_id=?,meal_id=?,quantity=?,order_price=?,order_date=? where order_id=?");
			preparedStatement.setInt(1, item.getClient().getClient_id());
			preparedStatement.setInt(2, item.getMeal().getMeal_id());
			preparedStatement.setInt(3, item.getQuantity());
			preparedStatement.setDouble(4, ((item.getQuantity()/1000)*item.getMeal().getPrice()));
			preparedStatement.setDate(5, new Date(System.currentTimeMillis()));
			preparedStatement.setInt(6, item.getOrder_id());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return item;
	}
	public void deleteOrder(int id) {
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("delete from orders where order_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		
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
		}
	}

	
}
