package org.innovect.assignment.dto;

import java.util.List;

import org.innovect.assignment.model.OrderSides;

public class SubmitOrderPostDTO {

	private List<OrderPizzaDTO> orderPizzaDTOList;

	private List<OrderSides> sideOrderList;

	public List<OrderPizzaDTO> getOrderPizzaDTOList() {
		return orderPizzaDTOList;
	}

	public void setOrderPizzaDTOList(List<OrderPizzaDTO> orderPizzaDTOList) {
		this.orderPizzaDTOList = orderPizzaDTOList;
	}

	public List<OrderSides> getSideOrderList() {
		return sideOrderList;
	}

	public void setSideOrderList(List<OrderSides> sideOrderList) {
		this.sideOrderList = sideOrderList;
	}
	
}
