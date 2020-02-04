package org.innovect.assignment.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SubmitOrderPostDTO {

	private String orderId;
	
	@NotEmpty(message="Customer name is required.")
	private String custName;
	
	@NotEmpty(message="Contact number is required.")
	private String contactNumber;
	
	@NotEmpty(message="Correct delivery address is required.")
	private String deliveryAddress;

	@NotNull
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
