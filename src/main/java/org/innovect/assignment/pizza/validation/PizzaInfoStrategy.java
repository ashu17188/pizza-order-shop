package org.innovect.assignment.pizza.validation;

import java.util.List;

import org.innovect.assignment.model.OrderPizza;
/**
 * This inteface resides at the root of hierarchy for the implementation of
 * Strategy Pattern (i.e Behavioral Design Pattern)
 * @author Ashutosh Shukla
 *
 */
public interface PizzaInfoStrategy {

	/**
	 * This method provides validates different types of Pizza.
	 * @param orderPizza pizza ordered by customer
	 * @param unavailableStuffNameList list of unavailable items in inventory.
	 * @return SUCCESSFUL or specific reason for failure.
	 */
	String validatePizza(OrderPizza orderPizza, List<String> unavailableStuffNameList);

	double calculateAdditionalCost(OrderPizza orderPizza, List<String> unavailableStuffNameList);
}
