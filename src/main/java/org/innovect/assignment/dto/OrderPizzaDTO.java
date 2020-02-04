package org.innovect.assignment.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderPizzaDTO {

	private String pizzaName;

	@NotEmpty(message="Category is required.")
	private String pizzaCategory;

	@NotEmpty(message="Pizza size is required.(i.e Large, Medium, Regular)")
	private String pizzaSize;

	@Min(value = 1, message = "Price should not be less than 1$")
	private double price;

	private String crust;

	@NotNull
	private List<OrderAdditionalStuffDTO> orderAdditionalStuffList;

	public OrderPizzaDTO() {}
	
	public OrderPizzaDTO(String pizzaName, String pizzaCategory, String pizzaSize, double price, String crust,
			List<OrderAdditionalStuffDTO> orderAdditionalStuffList) {
		super();
		this.pizzaName = pizzaName;
		this.pizzaCategory = pizzaCategory;
		this.pizzaSize = pizzaSize;
		this.price = price;
		this.crust = crust;
		this.orderAdditionalStuffList = orderAdditionalStuffList;
	}

	public String getPizzaName() {
		return pizzaName;
	}

	public void setPizzaName(String pizzaName) {
		this.pizzaName = pizzaName;
	}

	public String getPizzaCategory() {
		return pizzaCategory;
	}

	public void setPizzaCategory(String pizzaCategory) {
		this.pizzaCategory = pizzaCategory;
	}

	public String getPizzaSize() {
		return pizzaSize;
	}

	public void setPizzaSize(String pizzaSize) {
		this.pizzaSize = pizzaSize;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCrust() {
		return crust;
	}

	public void setCrust(String crust) {
		this.crust = crust;
	}

	public List<OrderAdditionalStuffDTO> getOrderAdditionalStuffList() {
		return orderAdditionalStuffList;
	}

	public void setOrderAdditionalStuffList(List<OrderAdditionalStuffDTO> orderAdditionalStuffList) {
		this.orderAdditionalStuffList = orderAdditionalStuffList;
	}

	@Override
	public String toString() {
		return "OrderPizzaDTO [pizzaName=" + pizzaName + ", pizzaCategory=" + pizzaCategory + ", pizzaSize=" + pizzaSize
				+ ", price=" + price + ", crust=" + crust + ", orderAdditionalStuffList=" + orderAdditionalStuffList
				+ "]";
	}
	
}
