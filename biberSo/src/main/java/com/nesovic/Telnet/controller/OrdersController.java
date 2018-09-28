package com.nesovic.Telnet.controller;


import java.sql.SQLException;
import java.util.ArrayList;

import com.nesovic.Telnet.DAO.OrdersDAO;
import com.nesovic.Telnet.model.Clients;
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
	public static ArrayList<Order> updateListOfOrders(ArrayList<Order> c) {
		return dao.updateListOfOrders(c);
	}
	public static void deleteOrder(int id) {
		dao.deleteOrder(id);
	}
	public static ArrayList<Order> OrdersScrollList(int offset){
		return dao.OrdersScrollList(offset);
	}
	public static ArrayList<Order> ReverseOrdersScrollList(int offset){
		return dao.ReverseOrdersScrollList(offset);
	}
	public static ArrayList<Order> ScrollOrdersByDate(int offset,String date){
		return dao.ScrollOrdersByDate(offset, date);
	}
	public static ArrayList<Order> SelectOrdersByDate(String date){
		return dao.selectOrdersByDate(date);
	}
	public static ArrayList<Order> ScrollOrdersByStartDate(int offset,String date){
		return dao.ScrollOrdersByStartDate(offset, date);
	}
	public static ArrayList<Order> ScrollOrdersByEndDate(int offset,String date){
		return dao.ScrollOrdersByEndDate(offset, date);
	}
	public static ArrayList<Order> SelectOrdersByClientAndStartdate(int offset,String startDate,int[] id){
		return dao.SelectOrdersByClientAndStartdate(offset, startDate, id);
	}
	public static ArrayList<Order> SelectOrdersByClientAndEnddate(int offset,String endDate,int[] id){
		return dao.SelectOrdersByClientAndEnddate(offset, endDate, id);
	}
	public static ArrayList<Order> SelectOrdersByClientAndDate(int offset,String Date,int[] id){
		return dao.SelectOrdersByClientAndDate(offset, Date, id);
	}
	public static ArrayList<Order> ScrollOrdersByPeriod(int offset,String startDate,String endDate){
		return dao.ScrollOrdersByPeriod(offset, startDate, endDate);
	}
	public static ArrayList<Order> ScrollOrdersCombination(int offset,String startDate,String endDate,int[] id){
		return dao.ScrollOrdersCombination(offset, startDate, endDate, id);
	}
	public static ArrayList<Order> ScrollOrdersByClient(int offset,int id){
		return dao.ScrollOrdersByClient(offset, id);
	}
	public static ArrayList<Order> SelectOrdersByClients(int offset,int[] idList){
		return dao.SelectOrdersByClients(offset, idList);
	}
}
