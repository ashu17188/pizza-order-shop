package org.innovect.assignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sides")
public class Sides {
	
	@Id
	@Column(name = "side_name")
	private String sideName;
	
	@Column(name = "price")
	private double price;

	@ManyToOne
	@JoinColumn(name = "order_id", insertable = false, updatable = false, nullable = true)
	private PizzaOrder order;
}
