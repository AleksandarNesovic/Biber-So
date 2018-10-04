package com.nesovic.Telnet.model;

public class Meal {
	
	private int meal_id;
	private Category category;
	private String name;
	private double price;
	private String link;
	private boolean piece;
	private int numberOfMeals;
	public int getNumberOfMeals() {
		return numberOfMeals;
	}

	public void setNumberOfMeals(int numberOfMeals) {
		this.numberOfMeals = numberOfMeals;
	}

	public Meal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category_id) {
		this.category = category_id;
	}

	public Meal(Category category, String name, double price, String link, boolean piece) {
		super();
		this.category = category;
		this.name = name;
		this.price = price;
		this.link = link;
		this.piece = piece;
	}

	public int getMeal_id() {
		return meal_id;
	}
	public void setMeal_id(int meal_id) {
		this.meal_id = meal_id;
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
	public boolean isPiece() {
		return piece;
	}

	public void setPiece(boolean piece) {
		this.piece = piece;
	}
	@Override
	public String toString() {
		return name;
	}
	
	

}
