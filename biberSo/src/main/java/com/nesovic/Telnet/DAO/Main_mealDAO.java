package com.nesovic.Telnet.DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.nesovic.Telnet.model.Main_meal;


public class Main_mealDAO {

	private Connection conn=null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public Main_mealDAO() {
		super();
	}
	public ArrayList<Main_meal> selectMeal(){
		ArrayList<Main_meal> lista=new ArrayList<>();
		Main_meal glavno=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM main_meal");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				glavno=new Main_meal();
				glavno.setMain_id(resultSet.getInt(1));
				glavno.setName(resultSet.getString(2));
				glavno.setPrice(resultSet.getDouble(3));
				glavno.setLink(resultSet.getString(4));
				lista.add(glavno);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public ArrayList<Main_meal> selectMealByPrice(){
		ArrayList<Main_meal> lista=new ArrayList<>();
		Main_meal glavno=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM main_meal order by price");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				glavno=new Main_meal();
				glavno.setMain_id(resultSet.getInt(1));
				glavno.setName(resultSet.getString(2));
				glavno.setPrice(resultSet.getDouble(3));
				glavno.setLink(resultSet.getString(4));
				lista.add(glavno);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public Main_meal selectMealById(int id){
		Main_meal glavno=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM main_meal where main_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			if(resultSet.next()) {
				glavno=new Main_meal();
				glavno.setMain_id(resultSet.getInt(1));
				glavno.setName(resultSet.getString(2));
				glavno.setPrice(resultSet.getDouble(3));
				glavno.setLink(resultSet.getString(4));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return glavno;
	}
	public Main_meal insertMeal(Main_meal g){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into main_meal(name,price,link) values (?,?,?)");
			preparedStatement.setString(1, g.getName());
			preparedStatement.setDouble(2, g.getPrice());
			preparedStatement.setString(3, g.getLink());
			preparedStatement.execute();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return g;
	}
	public Main_meal updateMeal(Main_meal g){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("update main_meal set name=?,price=?,link=? where main_id=?");
			preparedStatement.setString(1, g.getName());
			preparedStatement.setDouble(2, g.getPrice());
			preparedStatement.setString(3, g.getLink());
			preparedStatement.setInt(4, g.getMain_id());
			preparedStatement.execute();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return g;
	}
	public void deleteMeal(int id){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("delete from main_meal where main_id=?");
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
