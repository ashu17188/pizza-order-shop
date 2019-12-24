package org.innovect.assignment.dto;

import java.util.List;

public class OrderPizzaDTO {

	private String pizzaName;

	private String pizzaCategory;

	private String pizzaSize;

	private String price;

	private String crust;

//	private Order order;

	private List<OrderAdditionalStuffDTO> orderAdditionalStuffDTOList;

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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCrust() {
		return crust;
	}

	public void setCrust(String crust) {
		this.crust = crust;
	}

	public List<OrderAdditionalStuffDTO> getOrderAdditionalStuffDTOList() {
		return orderAdditionalStuffDTOList;
	}

	public void setOrderAdditionalStuffDTOList(List<OrderAdditionalStuffDTO> orderAdditionalStuffDTOList) {
		this.orderAdditionalStuffDTOList = orderAdditionalStuffDTOList;
	}

}
