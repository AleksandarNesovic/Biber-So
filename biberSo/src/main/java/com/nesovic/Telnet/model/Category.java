package com.nesovic.Telnet.model;

public class Category {
	
	private int category_id;
	private String name;
	private String link;
	public Category() {
		super();
	}
	public Category(String name, String link) {
		super();
		this.name = name;
		this.link = link;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "Category [category_id=" + category_id + ", name=" + name + ", link=" + link + "]";
	}
	

}
