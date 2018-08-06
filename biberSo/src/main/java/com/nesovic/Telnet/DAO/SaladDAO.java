package com.nesovic.Telnet.DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.nesovic.Telnet.model.Salad;

public class SaladDAO {

	private Connection conn=null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public SaladDAO() {
		super();
	}

	public ArrayList<Salad> selectSalad(){
		ArrayList<Salad> lista=new ArrayList<>();
		Salad salata=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM salad");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				salata=new Salad();
				salata.setSalad_id(resultSet.getInt(1));
				salata.setName(resultSet.getString(2));
				salata.setPrice(resultSet.getDouble(3));
				salata.setLink(resultSet.getString(4));
				lista.add(salata);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public Salad selectSaladById(int id){
		Salad salata=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM salad where salad_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			if(resultSet.next()) {
				salata=new Salad();
				salata.setSalad_id(resultSet.getInt(1));
				salata.setName(resultSet.getString(2));
				salata.setPrice(resultSet.getDouble(3));
				salata.setLink(resultSet.getString(4));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return salata;
	}
	public Salad insertSalad(Salad r){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into salad(name,price,link) values(?,?,?)");
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
	public Salad updateSalad(Salad r){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("update salad set name=?,price=?,link=? where salad_id=?");
			preparedStatement.setString(1, r.getName());
			preparedStatement.setDouble(2, r.getPrice());
			preparedStatement.setString(3, r.getLink());
			preparedStatement.setInt(4, r.getSalad_id());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		close();
		return r;
	}
	public void deleteSalad(int id) {
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("delete from salad where salad_id=?");
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
