package com.nesovic.Telnet.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.nesovic.Telnet.model.Dessert;


public class DessertDAO {
	
	private Connection conn=null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	
	public DessertDAO() {
		super();
	}
	public ArrayList<Dessert> selectDessert(){
		ArrayList<Dessert> lista=new ArrayList<>();
		Dessert desert=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM dessert");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				desert=new Dessert();
				desert.setDessert_id(resultSet.getInt(1));
				desert.setName(resultSet.getString(2));
				desert.setPrice(resultSet.getDouble(3));
				desert.setLink(resultSet.getString(4));
				lista.add(desert);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public Dessert selectDessertById(int id){
		Dessert desert=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM dessert where dessert_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			if(resultSet.next()) {
				desert=new Dessert();
				desert.setDessert_id(resultSet.getInt(1));
				desert.setName(resultSet.getString(2));
				desert.setPrice(resultSet.getDouble(3));
				desert.setLink(resultSet.getString(4));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return desert;
	}
	public Dessert insertDessert(Dessert r){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into dessert(name,price,link) values(?,?,?)");
			preparedStatement.setString(1, r.getName());
			preparedStatement.setDouble(2, r.getPrice());
			preparedStatement.setString(3, r.getLink());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		close();
		return r;
	}
	public Dessert updateDessert(Dessert r){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("update dessert set name=?,price=?,link=? where dessert_id=?");
			preparedStatement.setString(1, r.getName());
			preparedStatement.setDouble(2, r.getPrice());
			preparedStatement.setString(3, r.getLink());
			preparedStatement.setInt(4, r.getDessert_id());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		close();
		return r;
	}
	public void deleteDessert(int id) {
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("delete from dessert where dessert_id=?");
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
