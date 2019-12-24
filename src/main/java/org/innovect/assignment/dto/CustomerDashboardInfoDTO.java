package org.innovect.assignment.dto;

import java.util.List;

public class CustomerDashboardInfoDTO {

	private List<PizzaInfoDTO> pizzaInfoDTOList;

	private List<AdditionalStuffInfoDTO> additionalStuffDTOList;

	public List<PizzaInfoDTO> getPizzaInfoDTOList() {
		return pizzaInfoDTOList;
	}

	public void setPizzaInfoDTOList(List<PizzaInfoDTO> pizzaInfoDTOList) {
		this.pizzaInfoDTOList = pizzaInfoDTOList;
	}

	public List<AdditionalStuffInfoDTO> getAdditionalStuffDTOList() {
		return additionalStuffDTOList;
	}

	public void setAdditionalStuffDTOList(List<AdditionalStuffInfoDTO> additionalStuffDTOList) {
		this.additionalStuffDTOList = additionalStuffDTOList;
	}

	@Override
	public String toString() {
		return "CustomerDashboardInfoDTO [pizzaInfoDTOList=" + pizzaInfoDTOList + ", additionalStuffDTOList="
				+ additionalStuffDTOList + "]";
	}

}
