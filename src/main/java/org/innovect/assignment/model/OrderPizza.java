package org.innovect.assignment.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "order_pizza")
public class OrderPizza extends TackingInfo implements Serializable {

	private static final long serialVersionUID = 1681146336738954945L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "order_pizza_id", updatable = false, nullable = false)
	private String pizzaId;

	@Column(name = "pizza_name")
	private String pizzaName;

	@Column(name = "pizza_category")
	private String pizzaCategory;

	@Column(name = "pizza_size")
	private String pizzaSize;

	@Column(name = "price")
	private double price;

	@Column(name = "crust")
	private String crust;

	@ManyToOne
	@JoinColumn(name = "order_id", insertable = false, updatable = false, nullable = true)
	private Order order;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "order_pizza_id")
	private List<OrderAdditionalStuff> orderAdditionalStuffList;

	public OrderPizza() {
	}

	public String getPizzaId() {
		return pizzaId;
	}

	public void setPizzaId(String pizzaId) {
		this.pizzaId = pizzaId;
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

	public String getCrust() {
		return crust;
	}

	public void setCrust(String crust) {
		this.crust = crust;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderAdditionalStuff> getOrderAdditionalStuffList() {
		return orderAdditionalStuffList;
	}

	public void setOrderAdditionalStuffList(List<OrderAdditionalStuff> orderAdditionalStuffList) {
		this.orderAdditionalStuffList = orderAdditionalStuffList;
	}

	@Override
	public String toString() {
		return "OrderPizza [pizzaId=" + pizzaId + ", pizzaName=" + pizzaName + ", pizzaCategory=" + pizzaCategory
				+ ", pizzaSize=" + pizzaSize + ", price=" + price + ", crust=" + crust + ", order=" + order
				+ ", orderAdditionalStuffList=" + orderAdditionalStuffList + "]";
	}
	
}
