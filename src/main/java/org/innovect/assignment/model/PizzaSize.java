package org.innovect.assignment.model;

import javax.persistence.Embeddable;

@Embeddable
public class PizzaSize {
	
	private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
