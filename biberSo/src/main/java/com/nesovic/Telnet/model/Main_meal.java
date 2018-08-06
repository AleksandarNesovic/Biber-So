package com.nesovic.Telnet.model;

public class Main_meal {
	
	private int main_id;
	private String name;
	private double price;
	private String link;
	public Main_meal() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Main_meal(String name, double price, String link) {
		super();
		this.name = name;
		this.price = price;
		this.link = link;
	}
	
	public int getMain_id() {
		return main_id;
	}
	public void setMain_id(int main_id) {
		this.main_id = main_id;
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
		return name ;
	}
	
	

}
