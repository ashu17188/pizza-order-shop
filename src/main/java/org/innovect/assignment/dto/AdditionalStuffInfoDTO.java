package org.innovect.assignment.dto;

import org.springframework.hateoas.ResourceSupport;

public class AdditionalStuffInfoDTO extends ResourceSupport{

	private String stuffName;

	private String stuffCategory;

	private double price;

	private long stockQuantity;

	public AdditionalStuffInfoDTO() {}
	
	public AdditionalStuffInfoDTO(String stuffName, String stuffCategory, double price, long stockQuantity) {
		super();
		this.stuffName = stuffName;
		this.stuffCategory = stuffCategory;
		this.price = price;
		this.stockQuantity = stockQuantity;
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

	public long getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(long stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	@Override
	public String toString() {
		return "AdditionalStuffDTO [stuffName=" + stuffName + ", stuffCategory=" + stuffCategory + ", price=" + price
				+ ", stockQuantity=" + stockQuantity + "]";
	}
	
}
