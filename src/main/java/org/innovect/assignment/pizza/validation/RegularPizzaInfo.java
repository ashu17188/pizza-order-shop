package org.innovect.assignment.pizza.validation;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.innovect.assignment.model.AdditionalStuffCategoryEnum;
import org.innovect.assignment.model.OrderAdditionalStuff;
import org.innovect.assignment.model.OrderPizza;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;
import org.innovect.assignment.utils.PizzaShopConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.util.concurrent.AtomicDouble;

/**
 * This class follows Strategy Pattern for Regular Veg and Non Veg Pizza for
 * available ingredients and crusts.
 * 
 * @author Ashutosh Shukla
 *
 */
@Component
public class RegularPizzaInfo implements PizzaInfoStrategy {

	@Value("${pizza.order.shop.max.non.veg.toppings}")
	private String maxToppings;

	@Value("${pizza.order.shop.max.crust}")
	private String maxCrustAllowed;

	/**
	 * This method validates different Bussiness rules which have been set for valid
	 * Pizza processing.
	 * 
	 * @param orderPizza Pizza which will be validated before Order processing.
	 * @return response whether operation is successfull or not.
	 */
	public String validatePizza(OrderPizza orderPizza, List<String> unavailableStuffNameList) {
		AtomicInteger crustCount = new AtomicInteger(0);
		AtomicInteger nonVegToppingsCount = new AtomicInteger(0);

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

			vegPizzaValidation(orderPizza, stuff);
			nonVegPizzaValidation(orderPizza, stuff, nonVegToppingsCount);

			if (stuff.getStuffCategory().equalsIgnoreCase(AdditionalStuffCategoryEnum.CRUST.toString())) {
				crustCount.getAndIncrement();
				if (crustCount.get() == Integer.parseInt(maxCrustAllowed) + 1) {
					throw new RuntimeException("Only one type of crust can be selected for any pizza");
				}
			}
		});
		return PizzaShopConstants.SUCCESSFUL_OPERATION;
	}

	/**
	 * Validation for veg pizza
	 * 
	 * @param orderPizza Order containing Pizza and ingredients like toppings.
	 */
	private void vegPizzaValidation(OrderPizza orderPizza, OrderAdditionalStuff stuff) {
		if (orderPizza.getPizzaCategory().equalsIgnoreCase(PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory())) {
			// Vegetarian pizza cannot have a non-­vegetarian topping.
			if (stuff.getStuffCategory().equalsIgnoreCase(AdditionalStuffCategoryEnum.NON_VEG_TOPPINGS.getCategory())) {
				throw new RuntimeException("Vegetarian pizza cannot have a non-­vegetarian toppings.");
			}
		}
	}

	/**
	 * Validation for Non veg pizza
	 * 
	 * @param orderPizza Order containing Pizza and ingredients like toppings.
	 */
	private void nonVegPizzaValidation(OrderPizza orderPizza, OrderAdditionalStuff stuff,
			AtomicInteger nonVegToppingsCount) {

		if (orderPizza.getPizzaCategory().equalsIgnoreCase(PizzaInfoCategoryEnum.NON_VEGETARIAN.getCategory())) {
			// Vegetarian pizza cannot have a non-­vegetarian topping.;
			if (stuff.getStuffCategory().equalsIgnoreCase(AdditionalStuffCategoryEnum.VEG_TOPPINGS.getCategory())
					&& stuff.getStuffName().equalsIgnoreCase(PizzaShopConstants.PANEER_TOPPINGS)) {
				throw new RuntimeException("Non-­vegetarian pizza cannot have paneer toppings.");
			}

			// You can add only one of the non-­veg toppings in non-­vegetarian pizza.
			if (stuff.getStuffCategory().equalsIgnoreCase(AdditionalStuffCategoryEnum.NON_VEG_TOPPINGS.getCategory())) {

				// You can add only one of the non-­veg toppings in non-­vegetarian pizza.
				if (nonVegToppingsCount.incrementAndGet() == Integer.parseInt(maxToppings)) {
					throw new RuntimeException(
							"You can add only one of the non-­veg toppings in non-­vegetarian pizza.");
				}
			}
		}
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
