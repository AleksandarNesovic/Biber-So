package com.nesovic.Telnet.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.nesovic.Telnet.model.Grill;

public class GrillDAO {

	private Connection conn=null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public GrillDAO() {
		super();
	}
	public ArrayList<Grill> selectGrill(){
		ArrayList<Grill> lista=new ArrayList<>();
		Grill rostilj=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM grill");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				rostilj=new Grill();
				rostilj.setGrill_id(resultSet.getInt(1));
				rostilj.setName(resultSet.getString(2));
				rostilj.setPrice(resultSet.getDouble(3));
				rostilj.setLink(resultSet.getString(4));
				lista.add(rostilj);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public Grill selectGrillById(int id){
		Grill rostilj=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM grill where grill_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			if(resultSet.next()) {
				rostilj=new Grill();
				rostilj.setGrill_id(resultSet.getInt(1));
				rostilj.setName(resultSet.getString(2));
				rostilj.setPrice(resultSet.getDouble(3));
				rostilj.setLink(resultSet.getString(4));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return rostilj;
	}
	public Grill insertGrill(Grill r){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into grill(name,price,link) values(?,?,?)");
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
	public Grill updateGrill(Grill r){

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("update grill set name=?,price=?,link=? where grill_id=?");
			preparedStatement.setString(1, r.getName());
			preparedStatement.setDouble(2, r.getPrice());
			preparedStatement.setString(3, r.getLink());
			preparedStatement.setInt(4, r.getGrill_id());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		close();
		return r;
	}
	public void deleteGrill(int id) {
		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("delete from grill where grill_id=?");
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
