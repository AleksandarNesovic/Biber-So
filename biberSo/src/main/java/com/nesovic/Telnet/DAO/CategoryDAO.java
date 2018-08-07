package com.nesovic.Telnet.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.nesovic.Telnet.model.Category;

public class CategoryDAO {
	
	private Connection conn=null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	public CategoryDAO() {
		super();
	}
	public ArrayList<Category> selectCategory(){
		ArrayList<Category> lista=new ArrayList<>();
		Category category=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM category");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				category=new Category();
				category.setCategory_id(resultSet.getInt(1));
				category.setName(resultSet.getString(3));
				category.setLink(resultSet.getString(2));
				lista.add(category);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public Category selectCategoryById(int id){
		Category category=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM category where category_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			if(resultSet.next()) {
				category=new Category();
				category.setCategory_id(resultSet.getInt(1));
				category.setName(resultSet.getString(3));
				category.setLink(resultSet.getString(2));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return category;
	}
	public Category insertCategory(Category g){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into category(name,link) values (?,?)");
			preparedStatement.setString(1, g.getName());
			preparedStatement.setString(2, g.getLink());
			preparedStatement.execute();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return g;
	}
	public Category updateCategory(Category g){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("update category set name=?,link=? where category_id=?");
			preparedStatement.setString(1, g.getName());
			preparedStatement.setString(2, g.getLink());
			preparedStatement.setInt(3, g.getCategory_id());
			preparedStatement.execute();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return g;
	}
	public void deleteCategory(int id){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("delete from category where category_id=?");
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
