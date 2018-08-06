package com.nesovic.Telnet.model;

public class Clients {
	
	private int client_id;
	private String name;
	private String lastname;
	private String username;
	private String password;
	private String role;
	private String email;
	public Clients() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Clients(String name, String lastname, String username, String password, String role, String email) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.role = role;
		this.email = email;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return name + " " + lastname ;
	}
	
	

}
