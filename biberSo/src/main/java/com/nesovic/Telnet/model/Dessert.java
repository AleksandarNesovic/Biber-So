package com.nesovic.Telnet.model;

public class Dessert {
	
	private int dessert_id;
	private String name;
	private double price;
	private String link;
	public Dessert() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Dessert(String name, double price, String link) {
		super();
		this.name = name;
		this.price = price;
		this.link = link;
	}
	public int getDessert_id() {
		return dessert_id;
	}
	public void setDessert_id(int dessert_id) {
		this.dessert_id = dessert_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return name;
	}
	
	

}
