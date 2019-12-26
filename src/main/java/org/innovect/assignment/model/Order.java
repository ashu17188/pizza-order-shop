package org.innovect.assignment.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.innovect.assignment.utils.PizzaShopUtils;
import org.springframework.util.StringUtils;

import com.google.common.util.concurrent.AtomicDouble;

@Entity
@Table(name = "order_main")
public class Order extends TackingInfo implements Serializable {

	private static final long serialVersionUID = 1559908347912663433L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "order_id", updatable = false, nullable = false)
	private String orderId;

	@Column(name = "customer_name")
	private String custName;
	
	@Column(name = "contact_number")
	private String contactNumber;
	
	@Column(name = "delivery_address")
	private String deliveryAddress;
	
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "order_id")
	private List<OrderPizza> pizzaList;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "order_id")
	private List<OrderSides> sideOrderList;

	@Column(name = "total_amount")
	private double totalAmountToPay;

	/**
	 * This method add Pizza to Pizza List for Order processing.
	 * 
	 * @param orderPizza Pizza which is going to be added.
	 * @return response in String format whether addPizza is successful or throwing
	 *         exception because of validation issue.
	 */
	public String addPizza(OrderPizza orderPizza, List<String> unavailableStuffName) {
		String validationResponse = "";
		if (orderPizza.getPizzaSize().equals(PizzaShopUtils.LARGE_PIZZA)) {
			validationResponse = validateLargePizza(orderPizza, unavailableStuffName);
		} else {
			validationResponse = validateRegularAndMediumPizza(orderPizza, unavailableStuffName);
		}

		if (!PizzaShopUtils.SUCCESSFUL_OPERATION.equalsIgnoreCase(validationResponse)) {
			throw new RuntimeException(validationResponse);
		}
		getPizzaList().add(orderPizza);
		this.totalAmountToPay += orderPizza.getPrice();

		return PizzaShopUtils.SUCCESSFUL_OPERATION;
	}

	/**
	 * This method add Sides -i.e Cold Drinks etc- to current order.
	 * 
	 * @param orderSides Sides which will be added to Current order.
	 * @return response whether operation is successfull or not.
	 */
	public String addSideBars(OrderSides orderSides) {
		if (orderSides.getOrderedQuantity() == 0) {
			throw new RuntimeException(orderSides.getSideName() + " has been ordered with zero quantity.");
		}
		getSideOrderList().add(orderSides);
		this.totalAmountToPay += (orderSides.getPrice() * orderSides.getOrderedQuantity());
		return PizzaShopUtils.SUCCESSFUL_OPERATION;
	}

	/**
	 * This method validates different Bussiness rules which have been set for valid
	 * Pizza processing.
	 * 
	 * @param orderPizza Pizza which will be validated before Order processing.
	 * @return response whether operation is successfull or not.
	 */
	public String validateRegularAndMediumPizza(OrderPizza orderPizza, List<String> unavailableStuffNameList) {
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
					if (nonVegToppingsCount.incrementAndGet() == 2) {
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
	 * 
	 * @param orderPizza Pizza which has been ordered by Customer
	 * @param unavailableStuffNameList List of Addition Stuff which are empty
	 * @return response showing validation is successful or not. 
	 */
	public String validateLargePizza(OrderPizza orderPizza, List<String> unavailableStuffNameList) {
		AtomicDouble totalStuffAmount = new AtomicDouble(0.0);

		if (StringUtils.isEmpty(orderPizza.getOrderAdditionalStuffList())) {
			return PizzaShopUtils.SUCCESSFUL_OPERATION;
		}

		// Decending order sort according to Stuff price
		Collections.sort(orderPizza.getOrderAdditionalStuffList(), (x, y) -> {
			return x.getPrice() < y.getPrice() ? 1 : -1;
		});

		//Veg Pizza validation
		

		// Out of Stock Additional Stuff can not be ordered
		List<OrderAdditionalStuff> additionStuffList = orderPizza.getOrderAdditionalStuffList();
		for (int i = 0; i < additionStuffList.size(); i++) {

			// Non Vegetarian pizza Validations
			if (orderPizza.getPizzaCategory().equalsIgnoreCase(PizzaInfoCategoryEnum.NON_VEGETARIAN.getCategory())) {
				// Vegetarian pizza cannot have a non-­vegetarian topping.;
				if (additionStuffList.get(i).getStuffCategory().equalsIgnoreCase(AdditionalStuffCategoryEnum.VEG_TOPPINGS.getCategory())
						&& additionStuffList.get(i).getStuffName().equalsIgnoreCase("Paneer")) {
					throw new RuntimeException("Non-­vegetarian pizza cannot have paneer toppings.");
				}
			}
			//Cost would not be calculate for 1st two highest cost stuff.
			if(i<2)continue;
			
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
		this.totalAmountToPay += totalStuffAmount.get();
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public List<OrderPizza> getPizzaList() {
		if (null == pizzaList) {
			pizzaList = new ArrayList<>();
		}
		return pizzaList;
	}

	public void setPizzaList(List<OrderPizza> pizzaList) {
		this.pizzaList = pizzaList;
	}

	public List<OrderSides> getSideOrderList() {
		if (null == sideOrderList) {
			sideOrderList = new ArrayList<>();
		}
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

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", custName=" + custName + ", contactNumber=" + contactNumber
				+ ", deliveryAddress=" + deliveryAddress + ", pizzaList=" + pizzaList + ", sideOrderList="
				+ sideOrderList + ", totalAmountToPay=" + totalAmountToPay + "]";
	}
	
}
