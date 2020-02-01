package org.innovect.assignment.pizza.validation;

import java.util.Collections;
import java.util.List;

import org.innovect.assignment.model.AdditionalStuffCategoryEnum;
import org.innovect.assignment.model.OrderAdditionalStuff;
import org.innovect.assignment.model.OrderPizza;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;
import org.innovect.assignment.utils.PizzaShopUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.util.concurrent.AtomicDouble;

@Service
public class LargePizzaInfo implements PizzaInfoStrategy {

	/**
	 * 
	 * @param orderPizza               Pizza which has been ordered by Customer
	 * @param unavailableStuffNameList List of Addition Stuff which are empty
	 * @return response showing validation is successful or not.
	 */
	@Override
	public String validatePizza(OrderPizza orderPizza, List<String> unavailableStuffNameList) {

		if (StringUtils.isEmpty(orderPizza.getOrderAdditionalStuffList())) {
			return PizzaShopUtils.SUCCESSFUL_OPERATION;
		}

		// Decending order sort according to Stuff price
		Collections.sort(orderPizza.getOrderAdditionalStuffList(), (x, y) -> {
			return x.getPrice() < y.getPrice() ? 1 : -1;
		});

		// Veg Pizza validation

		// Out of Stock Additional Stuff can not be ordered
		List<OrderAdditionalStuff> additionStuffList = orderPizza.getOrderAdditionalStuffList();
		for (int i = 0; i < additionStuffList.size(); i++) {

			// Non Vegetarian pizza Validations
			if (orderPizza.getPizzaCategory().equalsIgnoreCase(PizzaInfoCategoryEnum.NON_VEGETARIAN.getCategory())) {
				// Vegetarian pizza cannot have a non-­vegetarian topping.;
				if (additionStuffList.get(i).getStuffCategory()
						.equalsIgnoreCase(AdditionalStuffCategoryEnum.VEG_TOPPINGS.getCategory())
						&& additionStuffList.get(i).getStuffName().equalsIgnoreCase("Paneer")) {
					throw new RuntimeException("Non-­vegetarian pizza cannot have paneer toppings.");
				}
			}
		}
		return PizzaShopUtils.SUCCESSFUL_OPERATION;
	}

	/**
	 * This method calculate total cost of Addition Stuff for Large Pizza
	 * 
	 * @param orderAdditionalStuffList Additional Stuff eg. Toppings, Side bars
	 * @return totalAmount
	 */
	public double calcStuffCostLargePizza(List<OrderAdditionalStuff> orderAdditionalStuffList) {
		double totalStuffCost = 0.0;
		// Decending order sort according to Stuff price
		Collections.sort(orderAdditionalStuffList, (x, y) -> {
			return x.getPrice() > y.getPrice() ? 1 : -1;
		});

		for (int i = 2; i < orderAdditionalStuffList.size(); i++) {
			totalStuffCost += orderAdditionalStuffList.get(i).getPrice();
		}
		return totalStuffCost;
	}

	@Override
	public double calculateAdditionalCost(OrderPizza orderPizza, List<String> unavailableStuffNameList) {
		AtomicDouble totalStuffAmount = new AtomicDouble(0.0);
		List<OrderAdditionalStuff> additionStuffList = orderPizza.getOrderAdditionalStuffList();
		for (int i = 0; i < additionStuffList.size(); i++) {

			// Cost would not be calculate for 1st two highest cost stuff.
			if (i < 2)
				continue;

			if (unavailableStuffNameList.size() != 0
					&& unavailableStuffNameList.contains(additionStuffList.get(i).getStuffName())) {
				throw new RuntimeException(additionStuffList.get(i).getStuffName() + " is out of stock.");
			}
			if (additionStuffList.get(i).getOrderedQuantity() == 0) {
				throw new RuntimeException(additionStuffList.get(i).getStuffName() + " has zero quantity ordered.");
			}

			totalStuffAmount
					.addAndGet(additionStuffList.get(i).getPrice() * additionStuffList.get(i).getOrderedQuantity());
		}
		return totalStuffAmount.get();
	}
}
