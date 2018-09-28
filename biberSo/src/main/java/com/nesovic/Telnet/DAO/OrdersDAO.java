package com.nesovic.Telnet.DAO;


import java.sql.Connection;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
	
	public int countRows(String sql) {
		int br=0;
		try {
			conn=DatabaseConnector.connect();
			
			preparedStatement=conn.prepareStatement(sql);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				br++;
			}
		} catch ( SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.close();
		return br;
	}
	public ArrayList<Order> selectOrders(){
		String sql="select * from orders";
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			
			conn=DatabaseConnector.connect();
			
			preparedStatement=conn.prepareStatement(sql);
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
				item.setDisplay(resultSet.getBoolean(8));
				
				lista.add(item);
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	finally {
			this.close();
	}
		for (Order order : lista) {
			order.setNumberOfElements(countRows(sql));
		}
		this.close();
		return lista;
	}
	public ArrayList<Order> selectOrdersByDate(String date){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select * from orders where order_date=? order by order_id DESC");
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
				item.setDisplay(resultSet.getBoolean(8));
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
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
				item.setDisplay(resultSet.getBoolean(8));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return item;
	}
	public Order insertOrder(Order item) {
		MealDAO dao=new MealDAO();
		Meal m=dao.selectMealById(item.getMeal().getMeal_id());
		String datum = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into orders(client_id,meal_id,quantity,order_price,order_date,piece,display) values (?,?,?,?,?,?,?)");
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
			preparedStatement.setBoolean(7, item.isDisplay());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException  e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
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
		//String datum = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("update orders set client_id=?,meal_id=?,quantity=?,order_price=?,order_date=?,piece=?,display=? where order_id=?");
			preparedStatement.setInt(1, item.getClient().getClient_id());
			preparedStatement.setInt(2, item.getMeal().getMeal_id());
			preparedStatement.setInt(3, item.getQuantity());
			preparedStatement.setDouble(4, (item.getQuantity()*m.getPrice())/1000);
			preparedStatement.setString(5, item.getOrder_date());
			preparedStatement.setBoolean(6, item.isPiece());
			preparedStatement.setBoolean(7, item.isDisplay());
			preparedStatement.setInt(8, item.getOrder_id());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return item;
	}
	public ArrayList<Order> updateListOfOrders(ArrayList<Order> items) {
		ArrayList<Order> lista=new ArrayList<>();
		MealDAO dao=new MealDAO();
		try {
			conn=DatabaseConnector.connect();
				for (Order order : items) {
					Meal m=dao.selectMealById(order.getMeal().getMeal_id());
				//	String datum = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
				preparedStatement=conn.prepareStatement("update orders set client_id=?,meal_id=?,quantity=?,order_price=?,order_date=?,piece=?,display=? where order_id=?");
				preparedStatement.setInt(1, order.getClient().getClient_id());
				preparedStatement.setInt(2, order.getMeal().getMeal_id());
				preparedStatement.setInt(3, order.getQuantity());
				preparedStatement.setDouble(4, (order.getQuantity()*m.getPrice())/1000);
				preparedStatement.setString(5, order.getOrder_date());
				preparedStatement.setBoolean(6, order.isPiece());
				preparedStatement.setBoolean(7, order.isDisplay());
				preparedStatement.setInt(8, order.getOrder_id());
				preparedStatement.execute();
				lista.add(order);
				
		
		
		} }catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
			finally {
				this.close();
		}
		return lista;
	}
	public ArrayList<Order> OrdersScrollList(int offset){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM orders order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
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
				item.setDisplay(resultSet.getBoolean(8));
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Order> ReverseOrdersScrollList(int offset){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM orders order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
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
				item.setDisplay(resultSet.getBoolean(8));
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	
	public ArrayList<Order> ScrollOrdersByDate(int offset,String date){
		String sql="SELECT * FROM orders where order_date='"+date+"'";
		int br=countRows(sql);
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM orders where order_date=? order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setString(1, date);
			preparedStatement.setInt(2, offset);
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
				item.setDisplay(resultSet.getBoolean(8));
				lista.add(item);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	finally {
			this.close();
	}
		
		for (Order order : lista) {
			order.setNumberOfElements(br);
		}
		this.close();
		return lista;
	}
	public ArrayList<Order> ScrollOrdersByStartDate(int offset,String date){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM orders where order_date >= ? OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
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
				item.setDisplay(resultSet.getBoolean(8));
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Order> ScrollOrdersByEndDate(int offset,String date){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM orders where order_date <= ? OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
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
				item.setDisplay(resultSet.getBoolean(8));
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Order> ScrollOrdersByPeriod(int offset,String startDate,String endDate){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM orders where order_date between ? and ? order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
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
				item.setDisplay(resultSet.getBoolean(8));
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Order> ScrollOrdersCombination(int offset,String startDate,String endDate,int[] idList){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			for (int id: idList) {
			
			preparedStatement=conn.prepareStatement("SELECT * FROM orders where client_id=? and order_date between ? and ? order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
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
				item.setDisplay(resultSet.getBoolean(8));
				lista.add(item);
			}}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Order> SelectOrdersByClientAndStartdate(int offset,String startDate,int[] idList){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			for (int id: idList) {
			
			preparedStatement=conn.prepareStatement("SELECT * FROM orders where client_id=? and order_date >=? OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, startDate);
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
				item.setDisplay(resultSet.getBoolean(8));
				lista.add(item);
			}}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			this.close();	
		}
		
		return lista;
	}
	public ArrayList<Order> SelectOrdersByClientAndEnddate(int offset,String endDate,int[] idList){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			for (int id: idList) {
			
			preparedStatement=conn.prepareStatement("SELECT * FROM orders where client_id=? and order_date <=? OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, id);
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
				item.setDisplay(resultSet.getBoolean(8));
				lista.add(item);
			}}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Order> SelectOrdersByClientAndDate(int offset,String Date,int[] idList){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			for (int id: idList) {
			
			preparedStatement=conn.prepareStatement("SELECT * FROM orders where client_id=? and order_date =? order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, Date);
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
				item.setDisplay(resultSet.getBoolean(8));
				lista.add(item);
			}}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Order> ScrollOrdersByClient(int offset,int id){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM orders where client_id=? order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, offset);
			
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
				item.setDisplay(resultSet.getBoolean(8));
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Order> SelectOrdersByClients(int offset,int[] idList){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		MealDAO daog=new MealDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			for (int id: idList) {
				
				preparedStatement=conn.prepareStatement("SELECT * FROM orders where client_id=? order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
				preparedStatement.setInt(1, id);
				preparedStatement.setInt(2, offset);
				
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
					item.setDisplay(resultSet.getBoolean(8));
					lista.add(item);
			}
			
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
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
		finally {
			this.close();
	}
		
	}
	
	
	
}
