package org.innovect.assignment.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.innovect.assignment.utils.PizzaShopUtils;

import com.google.common.util.concurrent.AtomicDouble;

@Entity
@Table(name = "order_main")
public class Order extends TackingInfo implements Serializable {

	private static final long serialVersionUID = 1559908347912663433L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private long orderId;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "order_id")
	private List<OrderPizza> pizzaList;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "order_id")
	private List<OrderSides> sideOrderList;

	@JoinColumn(name = "total_amount")
	private double totalAmountToPay;

	/**
	 * This method add Pizza to Pizza List for Order processing.
	 * 
	 * @param orderPizza Pizza which is going to be added.
	 * @return response in String format whether addPizza is successful or throwing
	 *         exception because of validation issue.
	 */
	public String addPizza(OrderPizza orderPizza, List<String> unavailableStuffName) {
		String validationResponse = validatePizza(orderPizza, unavailableStuffName);
		if (!PizzaShopUtils.SUCCESSFUL_OPERATION.equalsIgnoreCase(validationResponse)) {
			throw new RuntimeException(validationResponse);
		}
		this.pizzaList.add(orderPizza);
		this.totalAmountToPay += orderPizza.getPrice();

		// Cost calculation for additional stuff in Large Pizza Only.
		if (orderPizza.getPizzaSize().equalsIgnoreCase("Large")) {
			this.totalAmountToPay += calcStuffCostLargePizza(orderPizza.getOrderAdditionalStuffList());
		}
		return PizzaShopUtils.SUCCESSFUL_OPERATION;
	}

	/**
	 * This method add Sides -i.e Cold Drinks etc- to current order.
	 * 
	 * @param orderSides Sides which will be added to Current order.
	 * @return response whether operation is successfull or not.
	 */
	public String addSideBars(OrderSides orderSides) {
		this.sideOrderList.add(orderSides);
		this.totalAmountToPay += orderSides.getPrice();
		return PizzaShopUtils.SUCCESSFUL_OPERATION;
	}

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

		// Out of Stock Pizza can not be ordered
		orderPizza.getOrderAdditionalStuffList().forEach(stuff -> {
			if (unavailableStuffNameList.contains(stuff.getStuffName())) {
				throw new RuntimeException(stuff.getStuffName() + " is out of stock.");
			}

			// Cost Calculation for various Stuff corresponding to ordered Pizza which is
			// not Large.
			if (!orderPizza.getPizzaSize().equalsIgnoreCase("Large")) {
				totalStuffAmount.addAndGet(stuff.getPrice());
			}

			// Vegetarian pizza Validations
			if (orderPizza.getPizzaCategory().equalsIgnoreCase(PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.toString())) {
				// Vegetarian pizza cannot have a non-­vegetarian topping.
				if (stuff.getStuffCategory()
						.equalsIgnoreCase(AdditionalStuffCategoryEnum.NON_VEG_TOPPINGS.toString())) {
					throw new RuntimeException("Vegetarian pizza cannot have a non-­vegetarian topping.");
				}

			}

			// Non Vegetarian pizza Validations
			if (orderPizza.getPizzaCategory().equalsIgnoreCase(PizzaInfoCategoryEnum.NON_VEGETARIAN.toString())) {
				// Vegetarian pizza cannot have a non-­vegetarian topping.;
				if (stuff.getStuffCategory().equalsIgnoreCase(AdditionalStuffCategoryEnum.VEG_TOPPINGS.toString())
						&& stuff.getStuffName().equalsIgnoreCase("Paneer")) {
					throw new RuntimeException("Vegetarian pizza cannot have a non-­vegetarian topping.");
				}
				// You can add only one of the non-­veg toppings in non-­vegetarian pizza.
				if (stuff.getStuffCategory()
						.equalsIgnoreCase(AdditionalStuffCategoryEnum.NON_VEG_TOPPINGS.toString())) {

					// For Large Size any two toppings is allowed and no cost would be added.
					if (orderPizza.getPizzaSize().equalsIgnoreCase("Large")
							&& nonVegToppingsCount.incrementAndGet() == 3) {
						throw new RuntimeException(
								"You can add only two of the non-­veg toppings in non-­vegetarian pizza.");
					} else if (!orderPizza.getPizzaSize().equalsIgnoreCase("Large")
							&& nonVegToppingsCount.incrementAndGet() == 2) {
						throw new RuntimeException(
								"You can add only one of the non-­veg toppings in non-­vegetarian pizza.");
					}

				}
			}

		});
		this.totalAmountToPay += totalStuffAmount.get();
		return PizzaShopUtils.SUCCESSFUL_OPERATION;
	}

	/**
	 * This method calculate total cost of Addition Stuff for Large Pizza
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

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public List<OrderPizza> getPizzaList() {
		return pizzaList;
	}

	public void setPizzaList(List<OrderPizza> pizzaList) {
		this.pizzaList = pizzaList;
	}

	public List<OrderSides> getSideOrderList() {
		return sideOrderList;
	}

	public void setSideOrderList(List<OrderSides> sideOrderList) {
		this.sideOrderList = sideOrderList;
	}

	public double getTotalAmountToPay() {
		return totalAmountToPay;
	}

	public void setTotalAmountToPay(double totalAmountToPay) {
		this.totalAmountToPay = totalAmountToPay;
	}

}
