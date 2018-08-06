package com.nesovic.Telnet.model;


public class Orders {
	private int order_id;
	private Main_meal meal;
	private Grill grill;
	private Salad salad;
	private Dessert dessert;
	private int quantityMeal;
	private int quantityGrill;
	private int quantitySalad;
	private int quantityDessert;
	private Clients client;
	private String order_date;
	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}





	public Main_meal getMeal() {
		return meal;
	}





	public void setMeal(Main_meal meal) {
		this.meal = meal;
	}





	public Grill getGrill() {
		return grill;
	}





	public void setGrill(Grill grill) {
		this.grill = grill;
	}





	public Salad getSalad() {
		return salad;
	}





	public void setSalad(Salad salad) {
		this.salad = salad;
	}





	public Dessert getDessert() {
		return dessert;
	}





	public void setDessert(Dessert dessert) {
		this.dessert = dessert;
	}





	public int getQuantityMeal() {
		return quantityMeal;
	}





	public void setQuantityMeal(int quantityMeal) {
		this.quantityMeal = quantityMeal;
	}





	public int getQuantityGrill() {
		return quantityGrill;
	}





	public void setQuantityGrill(int quantityGrill) {
		this.quantityGrill = quantityGrill;
	}





	public int getQuantitySalad() {
		return quantitySalad;
	}





	public void setQuantitySalad(int quantitySalad) {
		this.quantitySalad = quantitySalad;
	}





	public int getQuantityDessert() {
		return quantityDessert;
	}





	public void setQuantityDessert(int quantityDessert) {
		this.quantityDessert = quantityDessert;
	}





	public Orders(Main_meal meal, Grill grill, Salad salad, Dessert dessert, int quantityMeal, int quantityGrill,
			int quantitySalad, int quantityDessert, Clients client, String order_date) {
		super();
		this.meal = meal;
		this.grill = grill;
		this.salad = salad;
		this.dessert = dessert;
		this.quantityMeal = quantityMeal;
		this.quantityGrill = quantityGrill;
		this.quantitySalad = quantitySalad;
		this.quantityDessert = quantityDessert;
		this.client = client;
		this.order_date = order_date;
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

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}





	@Override
	public String toString() {
		return "Orders [order_id=" + order_id + ", meal=" + meal + ", grill=" + grill + ", salad=" + salad
				+ ", dessert=" + dessert + ", quantityMeal=" + quantityMeal + ", quantityGrill=" + quantityGrill
				+ ", quantitySalad=" + quantitySalad + ", quantityDessert=" + quantityDessert + ", client=" + client
				+ ", order_date=" + order_date + "]";
	}

	





}
