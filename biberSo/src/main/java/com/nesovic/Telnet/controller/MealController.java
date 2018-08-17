package com.nesovic.Telnet.controller;

import java.util.ArrayList;


import com.nesovic.Telnet.DAO.MealDAO;
import com.nesovic.Telnet.model.Meal;

public class MealController {

	private static MealController controller;

	private MealController() {
		super();
	}
	public static MealController getInstance() {
		if(controller==null) controller=new MealController();
		return controller;
	}
	static MealDAO dm=new MealDAO();
	
	public static ArrayList<Meal> selectMeal(){
		return dm.selectMeal();
	}
	public static ArrayList<Meal> selectMealByCategory(int id){
		return dm.selectMealByCategory(id);
	}
	public static Meal selectMealById(int id) {
		return dm.selectMealById(id);
	}
	public static Meal insertMeal(Meal m) {
		return dm.insertMeal(m);
	}
	public static Meal updateMeal(Meal m) {
		return dm.updateMeal(m);
	}
	public static void deleteMeal(int id) {
		dm.deleteMeal(id);
	}
	public static ArrayList<Meal> scrollMeal(int offset){
		return dm.scrollMeal(offset);
	}
	public static ArrayList<Meal> scrollMealBycategory(int id,int offset){
		return dm.scrollMealByCategory(id, offset);
	}
	
}
