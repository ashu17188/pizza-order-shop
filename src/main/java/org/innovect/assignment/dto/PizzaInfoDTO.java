package org.innovect.assignment.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PizzaInfoDTO {

	private int pizzaInfoId;

	@NotEmpty(message="Pizza name is required.")
	private String pizzaName;

	@NotEmpty(message="Pizza Category is required.")
	private String pizzaCategory;

	@NotEmpty(message="Pizza size is required.")
	private String pizzaSize;

	@Size(min = 1, max = 2000, message = "Pizza price should be between 1 and 2000.")
	private double price;

	@Size(min = 10, max = 200, message = "About Me must be between 1 and 1000 characters")
	private long stockQuantity;

	public PizzaInfoDTO(){}
	
	public PizzaInfoDTO(int pizzaInfoId, String pizzaName, String pizzaCategory, String pizzaSize, double price,
			long stockQuantity) {
		super();
		this.pizzaInfoId  = pizzaInfoId;
		this.pizzaName = pizzaName;
		this.pizzaCategory = pizzaCategory;
		this.pizzaSize = pizzaSize;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public int getPizzaInfoId() {
		return pizzaInfoId;
	}

	public void setPizzaInfoId(int pizzaInfoId) {
		this.pizzaInfoId = pizzaInfoId;
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

	public long getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(long stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	@Override
	public String toString() {
		return "PizzaInfoDTO [pizzaInfoId=" + pizzaInfoId + ", pizzaName=" + pizzaName + ", pizzaCategory="
				+ pizzaCategory + ", pizzaSize=" + pizzaSize + ", price=" + price + ", stockQuantity=" + stockQuantity
				+ "]";
	}
	
}
