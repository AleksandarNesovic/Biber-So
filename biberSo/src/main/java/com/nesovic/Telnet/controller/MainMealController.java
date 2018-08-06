package com.nesovic.Telnet.controller;

import java.util.ArrayList;

import com.nesovic.Telnet.DAO.Main_mealDAO;
import com.nesovic.Telnet.model.Main_meal;

public class MainMealController {

	private static MainMealController controller;

	public MainMealController() {
		super();
	}
	public static MainMealController getInstance() {
		if(controller==null) controller=new MainMealController();
		return controller;
	}
	static Main_mealDAO dm=new Main_mealDAO();
	
	public static ArrayList<Main_meal> selectMeal(){
		return dm.selectMeal();
	}
	public static Main_meal selectMealById(int id) {
		return dm.selectMealById(id);
	}
	public static Main_meal insertMeal(Main_meal m) {
		return dm.insertMeal(m);
	}
	public static Main_meal updateMeal(Main_meal m) {
		return dm.updateMeal(m);
	}
	public static void deleteMeal(int id) {
		dm.deleteMeal(id);
	}
	
}
