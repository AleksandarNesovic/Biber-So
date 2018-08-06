package com.nesovic.Telnet.DAO;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;

import com.nesovic.Telnet.exception.AuthFaildException;
import com.nesovic.Telnet.model.Clients;
import com.nesovic.Telnet.model.Credentials;


public class ClientsDAO {

	private Connection conn=null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public ClientsDAO() {
		super();
	}
	public ArrayList<Clients> selectClients(){
		ArrayList<Clients> lista=new ArrayList<>();
		Clients client=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM clients");
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			while(resultSet.next()) {
				client=new Clients();
				client.setClient_id(resultSet.getInt(1));
				client.setName(resultSet.getString(2));
				client.setLastname(resultSet.getString(3));
				client.setUsername(resultSet.getString(4));
				client.setPassword(resultSet.getString(5));
				client.setEmail(resultSet.getString(6));
				client.setRole(resultSet.getString(7));
				lista.add(client);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return lista;
	}
	public Clients selectClientsById(int id){
		Clients client=null;

		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("SELECT * FROM clients where client_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			resultSet=preparedStatement.getResultSet();
			if(resultSet.next()) {
				client=new Clients();
				client.setClient_id(resultSet.getInt(1));
				client.setName(resultSet.getString(2));
				client.setLastname(resultSet.getString(3));
				client.setUsername(resultSet.getString(4));
				client.setPassword(resultSet.getString(5));
				client.setEmail(resultSet.getString(6));
				client.setRole(resultSet.getString(7));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return client;
	}
	public Clients insertClient(Clients c){
		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("insert into clients(name,lastname,username,password,email,role) values (?,?,?,?,?,?)");
			preparedStatement.setString(1, c.getName());
			preparedStatement.setString(2, c.getLastname());
			preparedStatement.setString(3, c.getUsername());
			preparedStatement.setString(4, encryptPassword(c.getPassword()));
			preparedStatement.setString(5, c.getEmail());
			preparedStatement.setString(6, c.getRole());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return c;
	}
	public Clients updateClient(Clients c){
		try {
			conn = DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("update clients set name=?,lastname=?,username=?,password=?,email=?,role=? where client_id=?");
			preparedStatement.setString(1, c.getName());
			preparedStatement.setString(2, c.getLastname());
			preparedStatement.setString(3, c.getUsername());
			preparedStatement.setString(4, encryptPassword(c.getPassword()));
			preparedStatement.setString(5, c.getEmail());
			preparedStatement.setString(6, c.getRole());
			preparedStatement.setInt(7, c.getClient_id());
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
		return c;
	}
	public void deleteClient(int id) {

		try {
			conn=DatabaseConnector.connect();
			preparedStatement=conn.prepareStatement("delete from clients where client_id=?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		close();
	}
	public Clients Login(Credentials crd) throws ClassNotFoundException, SQLException, AuthFaildException {
		Clients client=null;
		String password=encryptPassword(crd.getPassword());
		conn=DatabaseConnector.connect();
		preparedStatement=conn.prepareStatement("select * from clients where username=?");
		preparedStatement.setString(1, crd.getUsername());
		preparedStatement.execute();
		resultSet=preparedStatement.getResultSet();
		if(resultSet.next()) {
			client=new Clients();
			client.setClient_id(resultSet.getInt(1));
			client.setName(resultSet.getString(2));
			client.setLastname(resultSet.getString(3));
			client.setUsername(resultSet.getString(4));
			client.setPassword(resultSet.getString(5));
			client.setEmail(resultSet.getString(6));
			client.setRole(resultSet.getString(7));
		}
		
		String userPassword=client.getPassword();
		if(userPassword.equals(password)) {
			client.setPassword(userPassword);
		}else {
			System.out.println("Pogresna sifra!");
			throw new AuthFaildException();
		}
		close();
		return client;
	}
	private static String encryptPassword(String password) {
		String sha1 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(password.getBytes("UTF-8"));
			sha1 = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sha1;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
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
