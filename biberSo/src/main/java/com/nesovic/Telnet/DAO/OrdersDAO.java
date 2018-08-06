package com.nesovic.Telnet.DAO;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.nesovic.Telnet.model.Clients;
import com.nesovic.Telnet.model.Dessert;
import com.nesovic.Telnet.model.Grill;
import com.nesovic.Telnet.model.Main_meal;
import com.nesovic.Telnet.model.Orders;
import com.nesovic.Telnet.model.Salad;

public class OrdersDAO {
	
	private Connection conn=null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public OrdersDAO() {
		super();
	}
	
	public ArrayList<Orders> selectOrder_items(){
		ArrayList<Orders> lista=new ArrayList<>();
		Orders item=null;
		Main_mealDAO daog=new Main_mealDAO();
		GrillDAO daor=new GrillDAO();
		SaladDAO daos=new SaladDAO();
		DessertDAO daod=new DessertDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select * from orders");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				item=new Orders();
				item.setOrder_id(resultSet.getInt(1));
				
				Clients client=daoc.selectClientsById(resultSet.getInt(2));
				item.setClient(client);
				
				Main_meal glavno=daog.selectMealById(resultSet.getInt(3));
				item.setMeal(glavno);
				
				Grill rostilj=daor.selectGrillById(resultSet.getInt(4));
				item.setGrill(rostilj);
				
				Salad salata=daos.selectSaladById(resultSet.getInt(5));
				item.setSalad(salata);
				
				Dessert desert=daod.selectDessertById(resultSet.getInt(6));
				item.setDessert(desert);
				
				item.setQuantityMeal(resultSet.getInt(7));
				item.setQuantityGrill(resultSet.getInt(8));
				item.setQuantitySalad(resultSet.getInt(9));
				item.setQuantityDessert(resultSet.getInt(10));
				
				item.setOrder_date(resultSet.getString(11));
				
				lista.add(item);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public Orders selectOrder_itemsById(int id){
		Orders item=null;
		Main_mealDAO daog=new Main_mealDAO();
		GrillDAO daor=new GrillDAO();
		SaladDAO daos=new SaladDAO();
		DessertDAO daod=new DessertDAO();
		ClientsDAO daoc=new ClientsDAO();
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select * from orders where order_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			if(resultSet.next()) {
				item=new Orders();
				item.setOrder_id(resultSet.getInt(1));
				
				Clients client=daoc.selectClientsById(resultSet.getInt(2));
				item.setClient(client);
				
				Main_meal glavno=daog.selectMealById(resultSet.getInt(3));
				item.setMeal(glavno);
				
				Grill rostilj=daor.selectGrillById(resultSet.getInt(4));
				item.setGrill(rostilj);
				
				Salad salata=daos.selectSaladById(resultSet.getInt(5));
				item.setSalad(salata);
				
				Dessert desert=daod.selectDessertById(resultSet.getInt(6));
				item.setDessert(desert);
				
				item.setQuantityMeal(resultSet.getInt(7));
				item.setQuantityGrill(resultSet.getInt(8));
				item.setQuantitySalad(resultSet.getInt(9));
				item.setQuantityDessert(resultSet.getInt(10));
				
				item.setOrder_date(resultSet.getString(11));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return item;
	}
	public Orders insertOrder_item(Orders item) {
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into orders(client_id,main_id,grill_id,salad_id,dessert_id,quantity_meal,quantity_grill,quantity_salad,quantity_dessert,order_date) values (?,?,?,?,?,?,?,?,?,?)");
			preparedStatement.setInt(1, item.getClient().getClient_id());
			preparedStatement.setInt(2, item.getMeal().getMain_id());
			preparedStatement.setInt(3, item.getGrill().getGrill_id());
			preparedStatement.setInt(4, item.getSalad().getSalad_id());
			preparedStatement.setInt(5, item.getDessert().getDessert_id());
			preparedStatement.setInt(6, item.getQuantityMeal());
			preparedStatement.setInt(7, item.getQuantityGrill());
			preparedStatement.setInt(8, item.getQuantitySalad());
			preparedStatement.setInt(9, item.getQuantityDessert());
			preparedStatement.setString(10, item.getOrder_date());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return item;
	}
	public Orders updateOrder_item(Orders item) {
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("update orders set client_id=?,main_id=?,grill_id=?,salad_id=?,dessert_id=?,quantity_meal=?,quantity_grill=?,quantity_salad=?,quantity_dessert=?,order_date=? where order_id=?");
			preparedStatement.setInt(1, item.getClient().getClient_id());
			preparedStatement.setInt(2, item.getMeal().getMain_id());
			preparedStatement.setInt(3, item.getGrill().getGrill_id());
			preparedStatement.setInt(4, item.getSalad().getSalad_id());
			preparedStatement.setInt(5, item.getDessert().getDessert_id());
			preparedStatement.setInt(6, item.getQuantityMeal());
			preparedStatement.setInt(7, item.getQuantityGrill());
			preparedStatement.setInt(8, item.getQuantitySalad());
			preparedStatement.setInt(9, item.getQuantityDessert());
			preparedStatement.setString(10, item.getOrder_date());
			preparedStatement.setInt(11, item.getOrder_id());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return item;
	}
	public void deleteOrder_item(int id) {
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
