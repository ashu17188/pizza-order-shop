package org.innovect.assignment.dto;

import java.util.List;

public class SubmitOrderPostDTO {

	private List<OrderPizzaDTO> orderPizzaDTOList;

	private List<OrderSidesDTO> sideOrderList;

	public List<OrderPizzaDTO> getOrderPizzaDTOList() {
		return orderPizzaDTOList;
	}

	public void setOrderPizzaDTOList(List<OrderPizzaDTO> orderPizzaDTOList) {
		this.orderPizzaDTOList = orderPizzaDTOList;
	}

	public List<OrderSidesDTO> getSideOrderList() {
		return sideOrderList;
	}

	public void setSideOrderList(List<OrderSidesDTO> sideOrderList) {
		this.sideOrderList = sideOrderList;
	}
	
}
