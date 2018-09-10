package com.nesovic.Telnet.DAO;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.nesovic.Telnet.model.Category;
import com.nesovic.Telnet.model.Meal;


public class MealDAO {

	private Connection conn=null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public MealDAO() {
		super();
	}
	public ArrayList<Meal> selectMeal(){
		ArrayList<Meal> lista=new ArrayList<>();
		Meal meal=null;
		CategoryDAO dao=new CategoryDAO();

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM meals");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				meal=new Meal();
				meal.setMeal_id(resultSet.getInt(1));
				
				Category category=dao.selectCategoryById(resultSet.getInt(2));
				meal.setCategory(category);
				
				meal.setName(resultSet.getString(3));
				meal.setPrice(resultSet.getDouble(4));
				meal.setLink(resultSet.getString(5));
				meal.setPiece(resultSet.getBoolean(6));
				lista.add(meal);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public ArrayList<Meal> scrollMeal(int offset){
		ArrayList<Meal> lista=new ArrayList<>();
		Meal meal=null;
		CategoryDAO dao=new CategoryDAO();

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM meals OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, offset);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				meal=new Meal();
				meal.setMeal_id(resultSet.getInt(1));
				
				Category category=dao.selectCategoryById(resultSet.getInt(2));
				meal.setCategory(category);
				
				meal.setName(resultSet.getString(3));
				meal.setPrice(resultSet.getDouble(4));
				meal.setLink(resultSet.getString(5));
				meal.setPiece(resultSet.getBoolean(6));
				lista.add(meal);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public ArrayList<Meal> selectMealByPrice(){
		ArrayList<Meal> lista=new ArrayList<>();
		Meal meal=null;
		CategoryDAO dao=new CategoryDAO();

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM meals order by price");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				meal=new Meal();
				meal.setMeal_id(resultSet.getInt(1));
				
				Category category=dao.selectCategoryById(resultSet.getInt(2));
				meal.setCategory(category);
				
				meal.setName(resultSet.getString(3));
				meal.setPrice(resultSet.getDouble(4));
				meal.setLink(resultSet.getString(5));
				meal.setPiece(resultSet.getBoolean(6));
				lista.add(meal);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public ArrayList<Meal> selectMealByCategory(int id){
		ArrayList<Meal> lista=new ArrayList<>();
		Meal meal=null;
		CategoryDAO dao=new CategoryDAO();

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM meals where category_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				meal=new Meal();
				meal.setMeal_id(resultSet.getInt(1));
				
				Category category=dao.selectCategoryById(resultSet.getInt(2));
				meal.setCategory(category);
				
				meal.setName(resultSet.getString(3));
				meal.setPrice(resultSet.getDouble(4));
				meal.setLink(resultSet.getString(5));
				meal.setPiece(resultSet.getBoolean(6));
				lista.add(meal);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public ArrayList<Meal> scrollMealByCategory(int id,int offset){
		ArrayList<Meal> lista=new ArrayList<>();
		Meal meal=null;
		CategoryDAO dao=new CategoryDAO();

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM meals where category_id=? OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY");
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, offset);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				meal=new Meal();
				meal.setMeal_id(resultSet.getInt(1));
				
				Category category=dao.selectCategoryById(resultSet.getInt(2));
				meal.setCategory(category);
				
				meal.setName(resultSet.getString(3));
				meal.setPrice(resultSet.getDouble(4));
				meal.setLink(resultSet.getString(5));
				meal.setPiece(resultSet.getBoolean(6));
				lista.add(meal);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public Meal selectMealById(int id){
		Meal meal=null;
		CategoryDAO dao=new CategoryDAO();

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM meals where meal_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			if(resultSet.next()) {
				meal=new Meal();
				meal.setMeal_id(resultSet.getInt(1));
				
				Category category=dao.selectCategoryById(resultSet.getInt(2));
				meal.setCategory(category);
				
				meal.setName(resultSet.getString(3));
				meal.setPrice(resultSet.getDouble(4));
				meal.setLink(resultSet.getString(5));
				meal.setPiece(resultSet.getBoolean(6));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return meal;
	}
	public Meal insertMeal(Meal g){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into meals(category_id,name,price,link,piece) values (?,?,?,?,?)");
			preparedStatement.setInt(1, g.getCategory().getCategory_id());
			preparedStatement.setString(2, g.getName());
			preparedStatement.setDouble(3, g.getPrice());
			preparedStatement.setString(4, g.getLink());
			preparedStatement.setBoolean(5, g.isPiece());
			preparedStatement.execute();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return g;
	}
	public Meal updateMeal(Meal g){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("update meals set category_id=?,name=?,price=?,link=?,piece=? where meal_id=?");
			preparedStatement.setInt(1, g.getCategory().getCategory_id());
			preparedStatement.setString(2, g.getName());
			preparedStatement.setDouble(3, g.getPrice());
			preparedStatement.setString(4, g.getLink());
			preparedStatement.setBoolean(5, g.isPiece());
			preparedStatement.setInt(6, g.getMeal_id());
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
			preparedStatement=conn.prepareStatement("delete from meals where meal_id=?");
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
