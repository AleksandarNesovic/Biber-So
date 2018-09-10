package com.nesovic.Telnet.model;

public class Order {
	private int order_id;
	private Clients client;
	private Meal meal;
	private int quantity;
	private double order_price;
	private String order_date;
	private boolean piece;
	private boolean display;
	private int numberOfElements;
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean isPiece() {
		return piece;
	}

	public void setPiece(boolean piece) {
		this.piece = piece;
	}
	
	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public Order(Clients client, Meal meal, int quantity, double order_price, String order_date, boolean piece,
			boolean display) {
		super();
		this.client = client;
		this.meal = meal;
		this.quantity = quantity;
		this.order_price = order_price;
		this.order_date = order_date;
		this.piece = piece;
		this.display = display;
	}

	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public Clients getClient() {
		return client;
	}
	public void setClient(Clients client) {
		this.client = client;
	}
	public Meal getMeal() {
		return meal;
	}
	public void setMeal(Meal meal) {
		this.meal = meal;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getOrder_price() {
		return order_price;
	}
	public void setOrder_price(double order_price) {
		this.order_price = order_price;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", client=" + client + ", meal=" + meal + ", quantity=" + quantity
				+ ", order_price=" + order_price + ", order_date=" + order_date + "]";
	}
	
	

}
