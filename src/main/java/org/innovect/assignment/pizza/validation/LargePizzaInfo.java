package org.innovect.assignment.pizza.validation;

import java.util.Collections;
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
 * This class follows Strategy Pattern for Large Veg and Non Veg Pizza for
 * available ingredients and crusts.
 * 
 * @author Ashutosh Shukla
 *
 */
@Component
public class LargePizzaInfo implements PizzaInfoStrategy {

	@Value("${pizza.order.shop.max.crust}")
	private String maxCrustAllowed;

	@Value("${pizza.large.max.free.toppings}")
	private String freeToppings;

	/**
	 * This method validates both Veg and Non Veg pizza.
	 * 
	 * @param orderPizza               Pizza which has been ordered by Customer
	 * @param unavailableStuffNameList List of Addition Stuff which are empty
	 * @return response showing validation is successful or not.
	 */
	@Override
	public String validatePizza(OrderPizza orderPizza, List<String> unavailableStuffNameList) {
		if (StringUtils.isEmpty(orderPizza.getOrderAdditionalStuffList())) {
			return PizzaShopConstants.SUCCESSFUL_OPERATION;
		}
		Collections.sort(orderPizza.getOrderAdditionalStuffList(), (x, y) -> {
			return x.getPrice() < y.getPrice() ? 1 : -1;
		});

		AtomicInteger crustCount = new AtomicInteger(0);
		List<OrderAdditionalStuff> additionStuffList = orderPizza.getOrderAdditionalStuffList();
		for (int i = 0; i < additionStuffList.size(); i++) {
			nonVegPizzaValidation(orderPizza, additionStuffList.get(i));

			if (additionStuffList.get(i).getStuffCategory()
					.equalsIgnoreCase(AdditionalStuffCategoryEnum.CRUST.toString())) {
				crustCount.getAndIncrement();
				if (crustCount.get() == Integer.parseInt(maxCrustAllowed) + 1) {
					throw new RuntimeException("Only one type of crust can be selected for any pizza");
				}
			}
		}

		return PizzaShopConstants.SUCCESSFUL_OPERATION;
	}

	/**
	 * Validation for Non veg pizza
	 * 
	 * @param orderPizza Order containing Pizza and ingredients like toppings.
	 */
	private void nonVegPizzaValidation(OrderPizza orderPizza, OrderAdditionalStuff stuff) {

		// Non Vegetarian pizza Validations
		if (orderPizza.getPizzaCategory().equalsIgnoreCase(PizzaInfoCategoryEnum.NON_VEGETARIAN.getCategory())) {
			// Vegetarian pizza cannot have a non-­vegetarian topping.;
			if (stuff.getStuffCategory().equalsIgnoreCase(AdditionalStuffCategoryEnum.VEG_TOPPINGS.getCategory())
					&& stuff.getStuffName().equalsIgnoreCase(PizzaShopConstants.PANEER_TOPPINGS)) {
				throw new RuntimeException("Non-­vegetarian pizza cannot have paneer toppings.");
			}
		}

	}

	@Override
	public double calculateAdditionalCost(OrderPizza orderPizza, List<String> unavailableStuffNameList) {
		AtomicDouble totalStuffAmount = new AtomicDouble(0.0);
		List<OrderAdditionalStuff> additionStuffList = orderPizza.getOrderAdditionalStuffList();
		if (StringUtils.isEmpty(orderPizza.getOrderAdditionalStuffList())) {
			return totalStuffAmount.get();
		}
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
