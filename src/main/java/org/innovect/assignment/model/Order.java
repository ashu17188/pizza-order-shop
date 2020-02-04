package org.innovect.assignment.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.innovect.assignment.pizza.validation.LargePizzaInfo;
import org.innovect.assignment.pizza.validation.MediumPizzaInfo;
import org.innovect.assignment.pizza.validation.PizzaInfoStrategy;
import org.innovect.assignment.pizza.validation.RegularPizzaInfo;
import org.innovect.assignment.utils.PizzaShopConstants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Entity
@Table(name = "order_main")
@Service
public class Order extends TackingInfo implements Serializable, ApplicationContextAware {

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

	private static ApplicationContext context;

	/**
	 * This method add Pizza to Pizza List for Order processing.
	 * 
	 * @param orderPizza Pizza which is going to be added.
	 * @return response in String format whether addPizza is successful or throwing
	 *         exception because of validation issue.
	 */
	public String addPizza(OrderPizza orderPizza, List<String> unavailableStuffName) {
		String validationResponse = "";

		switch (orderPizza.getPizzaSize()) {
		case PizzaShopConstants.LARGE_PIZZA:
			PizzaInfoStrategy largePizzaInfo = context.getBean(LargePizzaInfo.class);
			validationResponse = largePizzaInfo.validatePizza(orderPizza, unavailableStuffName);
			this.totalAmountToPay += largePizzaInfo.calculateAdditionalCost(orderPizza, unavailableStuffName);
			break;

		case PizzaShopConstants.MEDIUM_PIZZA:
			PizzaInfoStrategy mediumPizzaInfo = context.getBean(MediumPizzaInfo.class);
			validationResponse = mediumPizzaInfo.validatePizza(orderPizza, unavailableStuffName);
			this.totalAmountToPay += mediumPizzaInfo.calculateAdditionalCost(orderPizza, unavailableStuffName);
			break;

		case PizzaShopConstants.REGULAR_PIZZA:
			PizzaInfoStrategy regularPizzaInfo = context.getBean(RegularPizzaInfo.class);
			validationResponse = regularPizzaInfo.validatePizza(orderPizza, unavailableStuffName);
			this.totalAmountToPay += regularPizzaInfo.calculateAdditionalCost(orderPizza, unavailableStuffName);
			break;
		}

		if (!PizzaShopConstants.SUCCESSFUL_OPERATION.equalsIgnoreCase(validationResponse)) {
			throw new RuntimeException(validationResponse);
		}
		getPizzaList().add(orderPizza);
		this.totalAmountToPay += orderPizza.getPrice();

		return PizzaShopConstants.SUCCESSFUL_OPERATION;
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
		return PizzaShopConstants.SUCCESSFUL_OPERATION;
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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static <T> T getBean(Class<T> beanClass) {
		return context.getBean(beanClass);
	}
}
