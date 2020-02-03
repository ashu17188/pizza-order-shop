package org.innovect.assignment.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.hateoas.ResourceSupport;

public class PizzaInfoDTO extends ResourceSupport {

	private int pizzaInfoId;

	@NotEmpty(message="Pizza name is required.")
	private String pizzaName;

	@NotEmpty(message="Pizza category is required.")
	private String pizzaCategory;

	@NotEmpty(message="Pizza size is required.")
	private String pizzaSize;

	@Min(value = 1, message = "Price should not be less than 1$")
	private double price;

	@Min(value = 1, message = "Stock quantity should not be less than 1 $")
    @Max(value = 10000, message = "Stock quantity should not be greater than 10000 $")
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
