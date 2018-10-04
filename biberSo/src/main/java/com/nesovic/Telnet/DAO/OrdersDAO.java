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
				e.printStackTrace();
			}
		}
	}
	private Order extractOrderFromResultSet(ResultSet rs) throws SQLException {
		Order item=new Order();
		
		item.setOrder_id(resultSet.getInt("order_id"));
		
		Clients client=new Clients();
		client.setClient_id(resultSet.getInt("client_id"));
		client.setName(resultSet.getString("name"));
		client.setLastname(resultSet.getString("lastname"));
		client.setUsername(resultSet.getString("username"));
		item.setClient(client);
		
		Meal main=new Meal();
		main.setMeal_id(resultSet.getInt("meal_id"));
		main.setName(resultSet.getString("mealName"));
		main.setPrice(resultSet.getDouble("price"));
		main.setLink(resultSet.getString("link"));
		main.setPiece(resultSet.getBoolean("piece"));
		item.setMeal(main);
		
		item.setQuantity(resultSet.getInt("quantity"));
		item.setOrder_price(resultSet.getDouble("order_price"));
		item.setOrder_date(resultSet.getString("order_date"));
		item.setPiece(resultSet.getBoolean("piece"));
		item.setDisplay(resultSet.getBoolean("display"));
		return item;
	}
	
//	public int countRows(String sql) {
//		int br=0;
//		try {
//			conn=DatabaseConnector.connect();
//			
//			preparedStatement=conn.prepareStatement(sql);
//			preparedStatement.execute();
//			resultSet=preparedStatement.getResultSet();
//			while(resultSet.next()) {
//				br++;
//			}
//		} catch ( SQLException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}finally{
//			this.close();
//		}
//		return br;
//	}
	public ArrayList<Order> selectOrders(){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		try {
			conn=DatabaseConnector.connect();
			
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			
			while(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
				
				lista.add(item);
			}
			for (Order order : lista) {
				order.setNumberOfElements(lista.size());
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Order> selectOrdersByDate(String date){
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id where order_date=? order by order_id DESC");
			preparedStatement.setString(1, date);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
				
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
		
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id where order_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			if(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
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
		
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, offset);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
				
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
		
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, offset);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
				
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
		
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id where order_date=? order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setString(1, date);
			preparedStatement.setInt(2, offset);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				item=extractOrderFromResultSet(resultSet);
				lista.add(item);
			}
			for (Order order : lista) {
				order.setNumberOfElements(lista.size());
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Order> ScrollOrdersByStartDate(int offset,String date){
		
		ArrayList<Order> lista=new ArrayList<>();
		Order item=null;
		
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id where order_date >= ? OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(2, offset);
			preparedStatement.setString(1, date);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
				
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
		
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id where order_date <= ? OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(2, offset);
			preparedStatement.setString(1, date);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
				
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
		
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id where order_date between ? and ? order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setString(1, startDate);
			preparedStatement.setString(2, endDate);
			preparedStatement.setInt(3, offset);
			
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
				
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
		
		try {
			conn=DatabaseConnector.connect();
			for (int id: idList) {
			
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id where o.client_id=? and order_date between ? and ? order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, startDate);
			preparedStatement.setString(3, endDate);
			preparedStatement.setInt(4, offset);
			
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
				
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
		
		try {
			conn=DatabaseConnector.connect();
			for (int id: idList) {
			
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id where o.client_id=? and order_date >=? OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, startDate);
			preparedStatement.setInt(3, offset);
			preparedStatement.execute();
			
			resultSet=preparedStatement.getResultSet();
			
			while(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
				
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
		
		try {
			conn=DatabaseConnector.connect();
			for (int id: idList) {
			
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id where o.client_id=? and order_date <=? OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, endDate);
			preparedStatement.setInt(3, offset);
			
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
				
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
		
		try {
			conn=DatabaseConnector.connect();
			for (int id: idList) {
			
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id where o.client_id=? and order_date =? order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, Date);
			preparedStatement.setInt(3, offset);
			
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
				
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
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id where o.client_id=? order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, offset);
			
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				
				item=extractOrderFromResultSet(resultSet);
				
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
		try {
			conn=DatabaseConnector.connect();
			for (int id: idList) {
				
				preparedStatement=conn.prepareStatement("select o.order_id, o.quantity,o.order_price, o.order_date,o.piece,o.display, c.client_id, c.name,c.lastname, c.username,c.password,c.role,c.email, m.meal_id, m.name as mealName, m.price,m.link,m.piece from orders o join clients c on o.client_id=c.client_id join meals m on o.meal_id=m.meal_id where o.client_id=? order by order_id DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
				preparedStatement.setInt(1, id);
				preparedStatement.setInt(2, offset);
				
				preparedStatement.execute();
				resultSet=preparedStatement.getResultSet();
				while(resultSet.next()) {
					
					item=extractOrderFromResultSet(resultSet);
					
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
