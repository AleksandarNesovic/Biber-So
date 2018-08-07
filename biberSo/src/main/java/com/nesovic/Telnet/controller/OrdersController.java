package com.nesovic.Telnet.controller;

import java.util.ArrayList;

import com.nesovic.Telnet.DAO.OrdersDAO;
import com.nesovic.Telnet.model.Order;

public class OrdersController {
	
	private static OrdersController controller;

	public OrdersController() {
		super();
	}
	
	public static OrdersController getInstance() {
		if(controller==null) controller=new OrdersController();
		return controller;
	}
	
	static OrdersDAO dao=new OrdersDAO();
	
	public static ArrayList<Order> selectOrders(){
		return dao.selectOrders();
	}
	public static Order selectOrderById(int id) {
		return dao.selectOrderById(id);
	}
	public static Order insertOrder(Order c) {
		return dao.insertOrder(c);
	}
	public static Order updateOrder(Order c) {
		return dao.updateOrder(c);
	}
	public static void deleteOrder(int id) {
		dao.deleteOrder(id);
	}
}
