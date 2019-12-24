package org.innovect.assignment.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_sides")
public class OrderSides  extends TackingInfo implements Serializable {
	
	private static final long serialVersionUID = -4951572004877935890L;

	@Id
	@Column(name = "side_name")
	private String sideName;
	
	@Column(name = "price")
	private double price;

	@ManyToOne
	@JoinColumn(name = "order_id", insertable = false, updatable = false, nullable = true)
	private Order order;

	public String getSideName() {
		return sideName;
	}

	public void setSideName(String sideName) {
		this.sideName = sideName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
