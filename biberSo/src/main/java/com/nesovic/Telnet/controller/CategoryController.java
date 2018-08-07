package com.nesovic.Telnet.controller;

import java.util.ArrayList;

import com.nesovic.Telnet.DAO.CategoryDAO;
import com.nesovic.Telnet.model.Category;

public class CategoryController {

	private static CategoryController controller;

	public CategoryController() {
		super();
	}
	public static CategoryController getInstance() {
		if(controller==null) controller=new CategoryController();
		return controller;
	}
	static CategoryDAO dc=new CategoryDAO();
	
	public static ArrayList<Category> selectCategory(){
		return dc.selectCategory();
	}
	public static Category selectCategoryById(int id) {
		return dc.selectCategoryById(id);
	}
	public static Category insertCategory(Category m) {
		return dc.insertCategory(m);
	}
	public static Category updateCategory(Category m) {
		return dc.updateCategory(m);
	}
	public static void deleteCategory(int id) {
		dc.deleteCategory(id);
	}
	
}
