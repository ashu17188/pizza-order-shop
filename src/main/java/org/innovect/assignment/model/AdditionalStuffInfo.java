package org.innovect.assignment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "additional_stuff")
public class AdditionalStuffInfo extends TackingInfo implements Serializable {

	private static final long serialVersionUID = 8081514754610293601L;

	@Id
	@Column(name = "stuff_name")
	private String stuffName;

	@Column(name = "stuff_category")
	private String stuffCategory;

	@Column(name = "price")
	private double price;

	/**
	 * Specifies whether pizza is available in inventory or not.
	 */
	@Column(name = "stock_quantity")
	private long stockQuantity;

	public AdditionalStuffInfo() {
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
		return "AdditionalStuffInfo [stuffName=" + stuffName + ", stuffCategory=" + stuffCategory + ", price=" + price
				+ ", stockQuantity=" + stockQuantity + "]";
	}
}
