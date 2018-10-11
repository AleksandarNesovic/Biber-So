package com.nesovic.Telnet.DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import com.nesovic.Telnet.model.Category;

public class CategoryDAO {
	
	private Connection conn=null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	public CategoryDAO() {
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
	private Category extractCategoryFromResultSet(ResultSet resultSet) throws SQLException {
		Category category=new Category();
		
		category.setCategory_id(resultSet.getInt("category_id"));
		category.setName(resultSet.getString("name"));
		category.setLink(resultSet.getString("link"));
		
		return category;
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
				category=extractCategoryFromResultSet(resultSet);
				lista.add(category);
			}
		} catch (ClassNotFoundException | SQLException | NamingException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
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
				category=extractCategoryFromResultSet(resultSet);
			}
		} catch (ClassNotFoundException | SQLException | NamingException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return category;
	}
	public Category insertCategory(Category g){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into category(name,link) values (?,?)");
			preparedStatement.setString(1, g.getName());
			preparedStatement.setString(2, g.getLink());
			preparedStatement.execute();

		} catch (ClassNotFoundException | SQLException | NamingException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
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

		} catch (ClassNotFoundException | SQLException | NamingException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
		return g;
	}
	public void deleteCategory(int id){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("delete from category where category_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();

		} catch (ClassNotFoundException | SQLException | NamingException e) {
			e.printStackTrace();
		}
		finally {
			this.close();
	}
	
	}

}
