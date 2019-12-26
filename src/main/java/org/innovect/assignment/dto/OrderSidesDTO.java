package org.innovect.assignment.dto;

public class OrderSidesDTO {

	private String sideName;

	private double price;

	private long orderedQuantity;

	public OrderSidesDTO() {}
	
	public OrderSidesDTO(String sideName, double price, long orderedQuantity) {
		super();
		this.sideName = sideName;
		this.price = price;
		this.orderedQuantity = orderedQuantity;
	}

	public String getSideName() {
		return sideName;
	}

	public void setSideName(String sideName) {
		this.sideName = sideName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(long orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	@Override
	public String toString() {
		return "OrderSidesDTO [sideName=" + sideName + ", price=" + price + ", orderedQuantity=" + orderedQuantity
				+ "]";
	}
}
