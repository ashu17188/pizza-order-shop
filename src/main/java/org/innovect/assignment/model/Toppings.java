package org.innovect.assignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "toppings")
public class Toppings {

	@Id
	@Column(name = "toppings_name")
	private String toppingsName;
	
	@Column(name = "toppings_category")
	private String toppingsCategory;

	@Column(name = "price")
	private double price;
	
	@ManyToOne
	@JoinColumn(name = "pizza_name", insertable = false, updatable = false, nullable = true)
	private Pizza pizza;

	public String getToppingsName() {
		return toppingsName;
	}

	public void setToppingsName(String toppingsName) {
		this.toppingsName = toppingsName;
	}

	public String getToppingsCategory() {
		return toppingsCategory;
	}

	public void setToppingsCategory(String toppingsCategory) {
		this.toppingsCategory = toppingsCategory;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
