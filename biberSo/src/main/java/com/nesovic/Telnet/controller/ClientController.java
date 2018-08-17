package com.nesovic.Telnet.controller;

import java.util.ArrayList;

import com.nesovic.Telnet.DAO.ClientsDAO;
import com.nesovic.Telnet.model.Clients;



public class ClientController {
 
	private static ClientController controller;

	private ClientController() {
		super();
	}
	public static ClientController getInstance() {
		if(controller==null) controller=new ClientController();
		return controller;
	}
	static ClientsDAO dc=new ClientsDAO();
	
	public static ArrayList<Clients> selectClients(){
		return dc.selectClients();
	}
	public static Clients selectClientById(int id) {
		return dc.selectClientsById(id);
	}
	public static Clients insertClient(Clients c) {
		return dc.insertClient(c);
	}
	public static Clients updateClient(Clients c) {
		return dc.updateClient(c);
	}
	public static void deleteClient(int id) {
		dc.deleteClient(id);
	}
	public static ArrayList<Clients> selectClientsByUsername(String username){
		return dc.selectClientsByUsername(username);
	}
}
