package com.nesovic.Telnet.controller;


import java.util.ArrayList;

import com.nesovic.Telnet.DAO.OrdersDAO;
import com.nesovic.Telnet.model.Order;

public class OrdersController {
	
	private static OrdersController controller;

	private OrdersController() {
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
	public static ArrayList<Order> selectOrdersByDate(String date){
		return dao.selectOrdersByDate(date);
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
	public static ArrayList<Order> OrdersScrollList(int offset){
		return dao.OrdersScrollList(offset);
	}
	public static ArrayList<Order> ScrollOrdersByDate(int offset,String date){
		return dao.ScrollOrdersByDate(offset, date);
	}
	public static ArrayList<Order> ScrollOrdersByPeriod(int offset,String startDate,String endDate){
		return dao.ScrollOrdersByPeriod(offset, startDate, endDate);
	}
	public ArrayList<Order> ScrollOrdersCombination(int offset,String startDate,String endDate,int id){
		return dao.ScrollOrdersCombination(offset, startDate, endDate, id);
	}
}
