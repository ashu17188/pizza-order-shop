package org.innovect.assignment.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pizza_order")
public class PizzaOrder extends TackingInfo implements Serializable {

	private static final long serialVersionUID = 1559908347912663433L;

	/**
	 * IP and timestamp based UUID (IETF RFC 4122 version 1)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private Integer orderId;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "order_id")
	private List<Pizza> pizzaList;

	
	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "order_id")
	private List<Sides> sideOrderList;
	
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public List<Pizza> getPizzaList() {
		return pizzaList;
	}

	public void setPizzaList(List<Pizza> pizzaList) {
		this.pizzaList = pizzaList;
	}

	public List<Sides> getSideOrderList() {
		return sideOrderList;
	}

	public void setSideOrderList(List<Sides> sideOrderList) {
		this.sideOrderList = sideOrderList;
	}
}
