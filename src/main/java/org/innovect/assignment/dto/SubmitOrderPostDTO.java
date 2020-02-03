package org.innovect.assignment.dto;

import java.util.List;

public class SubmitOrderPostDTO {

	private String orderId;
	
	private String custName;
	
	private String contactNumber;
	
	private String deliveryAddress;
	
	private List<OrderPizzaDTO> orderPizzaDTOList;

	private List<OrderSidesDTO> sideOrderList;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

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

	@Override
	public String toString() {
		return "SubmitOrderPostDTO [custName=" + custName + ", contactNumber=" + contactNumber + ", deliveryAddress="
				+ deliveryAddress + ", orderPizzaDTOList=" + orderPizzaDTOList + ", sideOrderList=" + sideOrderList
				+ "]";
	}
	
}
