package org.innovect.assignment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pizza_info")
public class PizzaInfo extends TackingInfo implements Serializable {

	private static final long serialVersionUID = 5173235498813370153L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pizza_info_id")
	private int pizzaInfoId;

	@Column(name = "pizza_name")
	private String pizzaName;

	@Column(name = "pizza_category")
	private String pizzaCategory;

	@Column(name = "pizza_size")
	private String pizzaSize;
	
	@Column(name = "price")
	private double price;

	/**
	 * Specifies whether pizza is available in inventory or not.
	 */
	@Column(name = "stock_quantity")
	private long stockQuantity;

	public PizzaInfo() {
	}

	public PizzaInfo(String pizzaName, String pizzaCategory, double price, long stockQuantity) {
		super();
		this.pizzaName = pizzaName;
		this.pizzaCategory = pizzaCategory;
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

}
