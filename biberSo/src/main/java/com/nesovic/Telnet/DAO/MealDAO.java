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
	private Meal extractMealFromResultSet(ResultSet resultSet) throws SQLException {
		Meal meal=new Meal();
		meal.setMeal_id(resultSet.getInt("meal_id"));
		
		Category category=new Category();
		category.setCategory_id(resultSet.getInt("category_id"));
		category.setLink(resultSet.getString("categoryLink"));
		category.setName(resultSet.getString("categoryName"));
		meal.setCategory(category);
		
		meal.setName(resultSet.getString("name"));
		meal.setPrice(resultSet.getDouble("price"));
		meal.setLink(resultSet.getString("link"));
		meal.setPiece(resultSet.getBoolean("piece"));
		
		return meal;
	}
	public ArrayList<Meal> selectMeal(){
		ArrayList<Meal> lista=new ArrayList<>();
		Meal meal=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select m.meal_id, m.name, m.price, m.link, m.piece, c.category_id, c.link as categoryLink, c.name as categoryName from meals m join category c on m.category_id=c.category_id");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				
				meal=extractMealFromResultSet(resultSet);
				
				lista.add(meal);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public int countMeals(String sql) {
		int br=0;
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement(sql);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				br++;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			this.close();
		}
		return br;
	}
	public ArrayList<Meal> scrollMeal(int offset){
		ArrayList<Meal> lista=new ArrayList<>();
		Meal meal=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select m.meal_id, m.name, m.price, m.link, m.piece, c.category_id, c.link as categoryLink, c.name as categoryName from meals m join category c on m.category_id=c.category_id OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
			preparedStatement.setInt(1, offset);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				meal=extractMealFromResultSet(resultSet);
				lista.add(meal);
			}
			for (Meal meal2 : lista) {
				meal2.setNumberOfMeals(lista.size());
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Meal> selectMealByPrice(){
		ArrayList<Meal> lista=new ArrayList<>();
		Meal meal=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select m.meal_id, m.name, m.price, m.link, m.piece, c.category_id, c.link as categoryLink, c.name as categoryName from meals m join category c on m.category_id=c.category_id order by price");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				
				meal=extractMealFromResultSet(resultSet);
				
				lista.add(meal);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Meal> selectMealByCategory(int id){
		ArrayList<Meal> lista=new ArrayList<>();
		Meal meal=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select m.meal_id, m.name, m.price, m.link, m.piece, c.category_id, c.link as categoryLink, c.name as categoryName from meals m join category c on m.category_id=c.category_id where c.category_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				meal=extractMealFromResultSet(resultSet);
				lista.add(meal);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public ArrayList<Meal> scrollMealByCategory(int id,int offset){
		ArrayList<Meal> lista=new ArrayList<>();
		Meal meal=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select m.meal_id, m.name, m.price, m.link, m.piece, c.category_id, c.link as categoryLink, c.name as categoryName from meals m join category c on m.category_id=c.category_id where c.category_id=? OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY");
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, offset);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				meal=extractMealFromResultSet(resultSet);
				lista.add(meal);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return lista;
	}
	public Meal selectMealById(int id){
		Meal meal=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("select m.meal_id, m.name, m.price, m.link, m.piece, c.category_id, c.link as categoryLink, c.name as categoryName from meals m join category c on m.category_id=c.category_id where meal_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			if(resultSet.next()) {
				meal=extractMealFromResultSet(resultSet);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
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
		finally {
			this.close();
	}
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
		finally {
			this.close();
	}
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
		finally {
			this.close();
	}
	
	}

}
