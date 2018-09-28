package com.nesovic.Telnet.model;

public class OrderClosed {

	private int orderClosed_id;
	private String order_date;
	private boolean status;
	
	public OrderClosed() {
		super();
	}
	
	public OrderClosed(String order_date, boolean status) {
		super();
		this.order_date = order_date;
		this.status = status;
	}
	
	public int getOrderClosed_id() {
		return orderClosed_id;
	}
	public void setOrderClosed_id(int orderClosed_id) {
		this.orderClosed_id = orderClosed_id;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
