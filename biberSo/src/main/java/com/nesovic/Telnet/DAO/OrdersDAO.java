package com.nesovic.Telnet.DAO;

import java.sql.Connection;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

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
				item.setPiece(resultSet.getBoolean(7));
				
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public ArrayList<Order> selectOrdersByDate(String date){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select * from orders where order_date=?");
			preparedStatement.setString(1, date);
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
				item.setPiece(resultSet.getBoolean(7));
				
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
				item.setPiece(resultSet.getBoolean(7));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return item;
	}
	public Order insertOrder(Order item) {
		MealDAO dao=new MealDAO();
		Meal m=dao.selectMealById(item.getMeal().getMeal_id());
		String datum = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into orders(client_id,meal_id,quantity,order_price,order_date,piece) values (?,?,?,?,?,?)");
			preparedStatement.setInt(1, item.getClient().getClient_id());
			preparedStatement.setInt(2, item.getMeal().getMeal_id());
			preparedStatement.setInt(3, item.getQuantity());
			if(item.isPiece()==true) {
				preparedStatement.setDouble(4, (item.getQuantity()*m.getPrice())/1000);
			}else {
				preparedStatement.setDouble(4, (item.getQuantity()*m.getPrice()));
			}
			preparedStatement.setString(5,datum);
			preparedStatement.setBoolean(6, item.isPiece());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException  e) {
			e.printStackTrace();
		}
		close();
		return item;
	}
	/*public ArrayList<Order> insertOrderList(ArrayList<Order> item) {
		for (Order order : item) {
		MealDAO dao=new MealDAO();
		Meal m=dao.selectMealById(order.getMeal().getMeal_id());
		String datum = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into orders(client_id,meal_id,quantity,order_price,order_date,piece) values (?,?,?,?,?,?)");
			preparedStatement.setInt(1, order.getClient().getClient_id());
			preparedStatement.setInt(2, order.getMeal().getMeal_id());
			preparedStatement.setInt(3, order.getQuantity());
			preparedStatement.setDouble(4, (order.getQuantity()*m.getPrice())/1000);
			preparedStatement.setString(5,datum);
			preparedStatement.setBoolean(6, order.isPiece());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException  e) {
			e.printStackTrace();
		}}
		close();
		return item;
	}*/
	public Order updateOrder(Order item) {
		MealDAO dao=new MealDAO();
		Meal m=dao.selectMealById(item.getMeal().getMeal_id());
		String datum = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("update orders set client_id=?,meal_id=?,quantity=?,order_price=?,order_date=?,piece=? where order_id=?");
			preparedStatement.setInt(1, item.getClient().getClient_id());
			preparedStatement.setInt(2, item.getMeal().getMeal_id());
			preparedStatement.setInt(3, item.getQuantity());
			preparedStatement.setDouble(4, (item.getQuantity()*m.getPrice())/1000);
			preparedStatement.setString(5, datum);
			preparedStatement.setBoolean(6, item.isPiece());
			preparedStatement.setInt(7, item.getOrder_id());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return item;
	}
	public ArrayList<Order> OrdersScrollList(int offset){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM orders OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, offset);
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
				item.setPiece(resultSet.getBoolean(7));
				
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public ArrayList<Order> ScrollOrdersByDate(int offset,String date){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM orders where order_date=? OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(2, offset);
			preparedStatement.setString(1, date);
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
				item.setPiece(resultSet.getBoolean(7));
				
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public ArrayList<Order> ScrollOrdersByPeriod(int offset,String startDate,String endDate){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM orders where order_date between ? and ? OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setString(1, startDate);
			preparedStatement.setString(2, endDate);
			preparedStatement.setInt(3, offset);
			
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
				item.setPiece(resultSet.getBoolean(7));
				
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public ArrayList<Order> ScrollOrdersCombination(int offset,String startDate,String endDate,int id){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM orders where client_id=? and order_date between ? and ? OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, startDate);
			preparedStatement.setString(3, endDate);
			preparedStatement.setInt(4, offset);
			
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
				item.setPiece(resultSet.getBoolean(7));
				
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
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
