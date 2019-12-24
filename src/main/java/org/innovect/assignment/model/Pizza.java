package org.innovect.assignment.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pizza")
public class Pizza extends TackingInfo implements Serializable {

	private static final long serialVersionUID = 1681146336738954945L;

	@Id
	@Column(name = "pizza_name")
	private String pizzaName;

	@Column(name = "pizza_category")
	private String pizzaCategory;

	@Column(name = "crust")
	private String crust;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "pizza_name")
	private List<Toppings> toppingsList;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "pizza_name")
	private List<AdditionalStuff> additionalStuffList;

	@ManyToOne
	@JoinColumn(name = "order_id", insertable = false, updatable = false, nullable = true)
	private PizzaOrder order;

	@Embedded
	@Column(name = "Regular")
	@AttributeOverrides({ @AttributeOverride(name = "price", column = @Column(name = "regular_price")) })
	private PizzaSize regularPizza;

	@Embedded
	@Column(name = "Medium")
	@AttributeOverrides({ @AttributeOverride(name = "price", column = @Column(name = "medium_price")) })
	private PizzaSize mediumPizza;

	@Embedded
	@Column(name = "Large")
	@AttributeOverrides({ @AttributeOverride(name = "price", column = @Column(name = "large_price")) })
	private PizzaSize largePizza;

	public Pizza() {
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

	public String getCrust() {
		return crust;
	}

	public void setCrust(String crust) {
		this.crust = crust;
	}

	public List<Toppings> getToppingsList() {
		return toppingsList;
	}

	public void setToppingsList(List<Toppings> toppingsList) {
		this.toppingsList = toppingsList;
	}

	public List<AdditionalStuff> getAdditionalStuffList() {
		return additionalStuffList;
	}

	public void setAdditionalStuffList(List<AdditionalStuff> additionalStuffList) {
		this.additionalStuffList = additionalStuffList;
	}

	public PizzaOrder getOrder() {
		return order;
	}

	public void setOrder(PizzaOrder order) {
		this.order = order;
	}

	public PizzaSize getRegularPizza() {
		return regularPizza;
	}

	public void setRegularPizza(PizzaSize regularPizza) {
		this.regularPizza = regularPizza;
	}

	public PizzaSize getMediumPizza() {
		return mediumPizza;
	}

	public void setMediumPizza(PizzaSize mediumPizza) {
		this.mediumPizza = mediumPizza;
	}

	public PizzaSize getLargePizza() {
		return largePizza;
	}

	public void setLargePizza(PizzaSize largePizza) {
		this.largePizza = largePizza;
	}
	
}
