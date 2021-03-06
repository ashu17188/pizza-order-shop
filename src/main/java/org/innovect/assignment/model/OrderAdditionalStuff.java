package org.innovect.assignment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "order_additional_stuff")
public class OrderAdditionalStuff extends TackingInfo implements Serializable {

	private static final long serialVersionUID = -121078458244400882L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "order_stuff_id", updatable = false, nullable = false)
	private String orderStuffId;

	@Column(name = "stuff_name")
	private String stuffName;

	@Column(name = "stuff_category")
	private String stuffCategory;

	@Column(name = "price")
	private double price;

	@Column(name = "ordered_quantity")
	private long orderedQuantity;

	@ManyToOne
	@JoinColumn(name = "order_pizza_id", insertable = false, updatable = false, nullable = true)
	private OrderPizza orderPizza;

	public String getOrderStuffId() {
		return orderStuffId;
	}

	public void setOrderStuffId(String orderStuffId) {
		this.orderStuffId = orderStuffId;
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

	public OrderPizza getOrderPizza() {
		return orderPizza;
	}

	public void setOrderPizza(OrderPizza orderPizza) {
		this.orderPizza = orderPizza;
	}

	@Override
	public String toString() {
		return "OrderAdditionalStuff [orderStuffId=" + orderStuffId + ", stuffName=" + stuffName + ", stuffCategory="
				+ stuffCategory + ", price=" + price + ", orderedQuantity=" + orderedQuantity + ", orderPizza="
				+ orderPizza + "]";
	}
}
