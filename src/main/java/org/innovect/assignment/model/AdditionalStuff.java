package org.innovect.assignment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "additional_stuff")
public class AdditionalStuff extends TackingInfo implements Serializable {

	private static final long serialVersionUID = 8081514754610293601L;

	@Id
	@Column(name = "stuff_name")
	private String stuffName;

	@Column(name = "stuff_category")
	private AdditionalStuffCategoryEnum stuffCategory;
	
	@Column(name = "price")
	private double price;

	@ManyToOne
	@JoinColumn(name = "pizza_name", insertable = false, updatable = false, nullable = true)
	private Pizza pizza;
	
	public AdditionalStuff() {
	}

	public AdditionalStuff(String stuffName, AdditionalStuffCategoryEnum stuffCategory, double price) {
		super();
		this.stuffName = stuffName;
		this.stuffCategory = stuffCategory;
		this.price = price;
	}

	public String getStuffName() {
		return stuffName;
	}

	public void setStuffName(String stuffName) {
		this.stuffName = stuffName;
	}

	public AdditionalStuffCategoryEnum getStuffCategory() {
		return stuffCategory;
	}

	public void setStuffCategory(AdditionalStuffCategoryEnum stuffCategory) {
		this.stuffCategory = stuffCategory;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}
}
