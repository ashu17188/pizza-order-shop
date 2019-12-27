package org.innovect.assignment.data;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.model.PizzaInfo;

public class PizzaInfoListData {

	public static List<PizzaInfo> createPizzaInfoList(){
		List<PizzaInfo> pizzaInfoList = new ArrayList<>();
		PizzaInfo pizzaInfo1 = new PizzaInfo("Deluxe Veggie","Vegetarian","Regular",150.00,20);
		PizzaInfo pizzaInfo2 = new PizzaInfo("Cheese and corn","Vegetarian","Large",475.00,5);
	
		pizzaInfoList.add(pizzaInfo1);
		pizzaInfoList.add(pizzaInfo2);
		return pizzaInfoList;
	}
}
