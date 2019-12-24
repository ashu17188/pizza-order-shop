package org.innovect.assignment.dto;

public class PizzaInfoDTO {

	private int pizzaInfoId;

	private String pizzaName;

	private String pizzaCategory;

	private String pizzaSize;

	private double price;

	private long stockQuantity;

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
