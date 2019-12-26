package org.innovect.assignment.dto;

public class OrderAdditionalStuffDTO {

	private String stuffName;

	private String stuffCategory;

	private double price;

	private long orderedQuantity;

	public OrderAdditionalStuffDTO() {}
	
	public OrderAdditionalStuffDTO(String stuffName, String stuffCategory, double price, long orderedQuantity) {
		super();
		this.stuffName = stuffName;
		this.stuffCategory = stuffCategory;
		this.price = price;
		this.orderedQuantity = orderedQuantity;
	}

	public String getStuffName() {
		return stuffName;
	}

	public void setStuffName(String stuffName) {
		this.stuffName = stuffName;
	}

	public String getStuffCategory() {
		return stuffCategory;
	}

	public void setStuffCategory(String stuffCategory) {
		this.stuffCategory = stuffCategory;
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
		return "OrderAdditionalStuffDTO [stuffName=" + stuffName + ", stuffCategory=" + stuffCategory + ", price="
				+ price + ", orderedQuantity=" + orderedQuantity + "]";
	}
}
