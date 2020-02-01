package org.innovect.assignment.pizza.validation;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.innovect.assignment.model.AdditionalStuffCategoryEnum;
import org.innovect.assignment.model.OrderPizza;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;
import org.innovect.assignment.utils.PizzaShopUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.util.concurrent.AtomicDouble;

@Service
public class RegularPizzaInfo implements PizzaInfoStrategy {

	/**
	 * This method validates different Bussiness rules which have been set for valid
	 * Pizza processing.
	 * 
	 * @param orderPizza Pizza which will be validated before Order processing.
	 * @return response whether operation is successfull or not.
	 */
	public String validatePizza(OrderPizza orderPizza, List<String> unavailableStuffNameList) {
		AtomicInteger nonVegToppingsCount = new AtomicInteger(0);
		AtomicDouble totalStuffAmount = new AtomicDouble(0.0);

		if (StringUtils.isEmpty(orderPizza.getOrderAdditionalStuffList())) {
			return PizzaShopUtils.SUCCESSFUL_OPERATION;
		}
		// Out of Stock Additional Stuff can not be ordered
		orderPizza.getOrderAdditionalStuffList().forEach(stuff -> {
			if (unavailableStuffNameList.size() != 0 && unavailableStuffNameList.contains(stuff.getStuffName())) {
				throw new RuntimeException(stuff.getStuffName() + " is out of stock.");
			}
			if (stuff.getOrderedQuantity() == 0) {
				throw new RuntimeException(stuff.getStuffName() + " has zero quantity ordered.");
			}

			// Cost Calculation for various Stuff corresponding to ordered Pizza which is
			// not Large.
			totalStuffAmount.addAndGet(stuff.getPrice() * stuff.getOrderedQuantity());

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
					if (nonVegToppingsCount.incrementAndGet() == PizzaShopUtils.MAX_TOPPINGS) {
						throw new RuntimeException(
								"You can add only one of the non-­veg toppings in non-­vegetarian pizza.");
					}
				}
			}

		});
		return PizzaShopUtils.SUCCESSFUL_OPERATION;
	}

	@Override
	public double calculateAdditionalCost(OrderPizza orderPizza, List<String> unavailableStuffNameList) {
		AtomicDouble totalStuffAmount = new AtomicDouble(0.0);
		// Out of Stock Additional Stuff can not be ordered
		orderPizza.getOrderAdditionalStuffList().forEach(stuff -> {
			// Cost Calculation for various Stuff corresponding to ordered Pizza which is
			// not Large.
			totalStuffAmount.addAndGet(stuff.getPrice() * stuff.getOrderedQuantity());

		});
		return totalStuffAmount.get();
	}

}
