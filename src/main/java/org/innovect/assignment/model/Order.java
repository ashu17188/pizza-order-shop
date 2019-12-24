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
@Table(name = "order_main")
public class Order extends TackingInfo implements Serializable {

	private static final long serialVersionUID = 1559908347912663433L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private Integer orderId;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "order_id")
	private List<OrderPizza> pizzaList;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "order_id")
	private List<OrderSides> sideOrderList;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public List<OrderPizza> getPizzaList() {
		return pizzaList;
	}

	public void setPizzaList(List<OrderPizza> pizzaList) {
		this.pizzaList = pizzaList;
	}

	public List<OrderSides> getSideOrderList() {
		return sideOrderList;
	}

	public void setSideOrderList(List<OrderSides> sideOrderList) {
		this.sideOrderList = sideOrderList;
	}
}
