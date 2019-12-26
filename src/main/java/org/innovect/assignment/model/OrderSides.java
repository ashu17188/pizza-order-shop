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
@Table(name = "order_sides")
public class OrderSides  extends TackingInfo implements Serializable {
	
	private static final long serialVersionUID = -4951572004877935890L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "order_side_id", updatable = false, nullable = false)
	private String orderSideId;
	
	@Column(name = "side_name")
	private String sideName;
	
	@Column(name = "price")
	private double price;

	@ManyToOne
	@JoinColumn(name = "order_id", insertable = false, updatable = false, nullable = true)
	private Order order;

	@Column(name = "ordered_quantity")
	private long orderedQuantity;

	@Column(name = "side_category")
	private String sideCategory;
	
	public String getOrderSideId() {
		return orderSideId;
	}

	public void setOrderSideId(String orderSideId) {
		this.orderSideId = orderSideId;
	}

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

	public long getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(long orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public String getSideCategory() {
		return sideCategory;
	}

	public void setSideCategory(String sideCategory) {
		this.sideCategory = sideCategory;
	}

	@Override
	public String toString() {
		return "OrderSides [orderSideId=" + orderSideId + ", sideName=" + sideName + ", price=" + price + ", order="
				+ order + ", orderedQuantity=" + orderedQuantity + ", sideCategory=" + sideCategory + "]";
	}
	
}
