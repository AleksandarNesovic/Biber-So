package com.nesovic.Telnet.model;

public class Salad {
	
	private int salad_id;
	private String name;
	private double price;
	private String link;
	public Salad() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Salad(String name, double price, String link) {
		super();
		this.name = name;
		this.price = price;
		this.link = link;
	}
	public int getSalad_id() {
		return salad_id;
	}
	public void setSalad_id(int salata_id) {
		this.salad_id = salata_id;
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
