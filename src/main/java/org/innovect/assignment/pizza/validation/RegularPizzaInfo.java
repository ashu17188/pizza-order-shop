package org.innovect.assignment.pizza.validation;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.innovect.assignment.model.AdditionalStuffCategoryEnum;
import org.innovect.assignment.model.OrderPizza;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;
import org.innovect.assignment.utils.PizzaShopConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.util.concurrent.AtomicDouble;

@Component
public class RegularPizzaInfo implements PizzaInfoStrategy {

	@Value("${pizza.order.shop.max.toppings}")
	private String maxToppings;

	/**
	 * This method validates different Bussiness rules which have been set for valid
	 * Pizza processing.
	 * 
	 * @param orderPizza Pizza which will be validated before Order processing.
	 * @return response whether operation is successfull or not.
	 */
	public String validatePizza(OrderPizza orderPizza, List<String> unavailableStuffNameList) {
		AtomicInteger nonVegToppingsCount = new AtomicInteger(0);
		AtomicInteger crustCount = new AtomicInteger(0);
		
		if (StringUtils.isEmpty(orderPizza.getOrderAdditionalStuffList())) {
			return PizzaShopConstants.SUCCESSFUL_OPERATION;
		}
		// Out of Stock Additional Stuff can not be ordered
		orderPizza.getOrderAdditionalStuffList().forEach(stuff -> {
			if (unavailableStuffNameList.size() != 0 && unavailableStuffNameList.contains(stuff.getStuffName())) {
				throw new RuntimeException(stuff.getStuffName() + " is out of stock.");
			}
			if (stuff.getOrderedQuantity() == 0) {
				throw new RuntimeException(stuff.getStuffName() + " has zero quantity ordered.");
			}
			
			// Vegetarian pizza Validations
			if (orderPizza.getPizzaCategory().equalsIgnoreCase(PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory())) {
				// Vegetarian pizza cannot have a non-­vegetarian topping.
				if (stuff.getStuffCategory()
						.equalsIgnoreCase(AdditionalStuffCategoryEnum.NON_VEG_TOPPINGS.getCategory())) {
					throw new RuntimeException("Vegetarian pizza cannot have a non-­vegetarian toppings.");
				}

			}

			// Non Vegetarian pizza Validations
			if (orderPizza.getPizzaCategory().equalsIgnoreCase(PizzaInfoCategoryEnum.NON_VEGETARIAN.getCategory())) {
				// Vegetarian pizza cannot have a non-­vegetarian topping.;
				if (stuff.getStuffCategory().equalsIgnoreCase(AdditionalStuffCategoryEnum.VEG_TOPPINGS.getCategory())
						&& stuff.getStuffName().equalsIgnoreCase("Paneer")) {
					throw new RuntimeException("Non-­vegetarian pizza cannot have paneer toppings.");
				}

				// You can add only one of the non-­veg toppings in non-­vegetarian pizza.
				if (stuff.getStuffCategory()
						.equalsIgnoreCase(AdditionalStuffCategoryEnum.NON_VEG_TOPPINGS.getCategory())) {

					// You can add only one of the non-­veg toppings in non-­vegetarian pizza.
					if (nonVegToppingsCount.incrementAndGet() == Integer.parseInt(maxToppings)) {
						throw new RuntimeException(
								"You can add only one of the non-­veg toppings in non-­vegetarian pizza.");
					}
				}
			}
			if (stuff.getStuffCategory().equalsIgnoreCase(AdditionalStuffCategoryEnum.CRUST.toString())) {
				crustCount.getAndIncrement();
				if (crustCount.get() == 2) {
					throw new RuntimeException("Only one type of crust can be selected for any pizza");
				}
			}
		});
		return PizzaShopConstants.SUCCESSFUL_OPERATION;
	}

	@Override
	public double calculateAdditionalCost(OrderPizza orderPizza, List<String> unavailableStuffNameList) {
		AtomicDouble totalStuffAmount = new AtomicDouble(0.0);
		
		if (StringUtils.isEmpty(orderPizza.getOrderAdditionalStuffList())) {
			return totalStuffAmount.get();
		}
		// Out of Stock Additional Stuff can not be ordered
		orderPizza.getOrderAdditionalStuffList().stream().forEach(stuff -> {
			// Cost Calculation for various Stuff corresponding to ordered Pizza which is
			// not Large.
			totalStuffAmount.addAndGet(stuff.getPrice() * stuff.getOrderedQuantity());

		});
		return totalStuffAmount.get();
	}

}
