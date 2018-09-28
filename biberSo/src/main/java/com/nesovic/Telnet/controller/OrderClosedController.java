package com.nesovic.Telnet.controller;

import com.nesovic.Telnet.DAO.OrderClosedDAO;

public class OrderClosedController {

	private static OrderClosedController controller;

	public OrderClosedController() {
		super();
	}
	public static OrderClosedController getInstance() {
		if(controller==null) controller=new OrderClosedController();
		return controller;
	}
	static OrderClosedDAO dao=new OrderClosedDAO();
	
	public static void insertOrderClosed(String date,boolean status) {
		dao.insertOrderClosed(date, status);
	}
	public static boolean checkOrderClosed(String date) {
		return dao.checkOrderClosed(date);
	}
	public static void updateOrderClosed(String date, boolean status) {
		dao.updateOrderClosed(date, status);
	}
	
}
