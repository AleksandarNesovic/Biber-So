package com.nesovic.Telnet.model;

public class Grill {
	
	private int grill_id;
	private String name;
	private double price;
	private String link;
	public Grill() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Grill(String name, double price, String link) {
		super();
		this.name = name;
		this.price = price;
		this.link = link;
	}
	
	public int getGrill_id() {
		return grill_id;
	}
	public void setGrill_id(int grill_id) {
		this.grill_id = grill_id;
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
		return  name;
	}
	
	

}
