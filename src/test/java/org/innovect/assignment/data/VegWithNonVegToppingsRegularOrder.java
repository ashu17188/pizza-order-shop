package org.innovect.assignment.data;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.dto.OrderAdditionalStuffDTO;
import org.innovect.assignment.dto.OrderPizzaDTO;
import org.innovect.assignment.dto.OrderSidesDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;

public class VegWithNonVegToppingsRegularOrder {

	/**
	 * @return Prebuilt SubmitOrderPostDTO Object for testing various scenarios
	 */
	public static SubmitOrderPostDTO createSubmitOrderPostDTOObject(){
		SubmitOrderPostDTO submitOrderPostDTO = new SubmitOrderPostDTO();
		submitOrderPostDTO.setCustName("David Johnson");
		submitOrderPostDTO.setOrderId("80870b18-a3c6-49c8-93cd-3ad631ebda2d");
		submitOrderPostDTO.setContactNumber("+91-8989161123");
		submitOrderPostDTO.setDeliveryAddress("K-10, BINAWAT TOWNSHIP, HADAPSAR PUNE-411028(M.H)");
		
		List<OrderSidesDTO> sideOrderList = new ArrayList<>();
		OrderSidesDTO orderSidesDTO1 = new OrderSidesDTO("Cold drink",55.0, 5);
		OrderSidesDTO orderSidesDTO2 = new OrderSidesDTO("Mousse cake",90.0, 5);
		//725
		sideOrderList.add(orderSidesDTO1);
		sideOrderList.add(orderSidesDTO2);
		
		submitOrderPostDTO.setOrderPizzaDTOList(createOrderPizzaDTOList());
		submitOrderPostDTO.setSideOrderList(sideOrderList);
		return submitOrderPostDTO;
	}
	
	private static List<OrderPizzaDTO> createOrderPizzaDTOList(){
		List<OrderPizzaDTO> orderPizzaDTOList = new ArrayList<>();
		
		List<OrderAdditionalStuffDTO> orderAdditionalStuffDTOList = new ArrayList<>();
		OrderAdditionalStuffDTO orderAdditionalStuffDTO1 = new OrderAdditionalStuffDTO("Black olive", "Veg Toppings", 20.0, 9);
		orderAdditionalStuffDTOList.add(orderAdditionalStuffDTO1);
		
		OrderAdditionalStuffDTO orderAdditionalStuffDTO2 = new OrderAdditionalStuffDTO("Paneer", "Veg Toppings", 35.0, 9);
		orderAdditionalStuffDTOList.add(orderAdditionalStuffDTO2);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO3 = new OrderAdditionalStuffDTO("Extra cheese", "miscellaneous", 35.0, 5);
		orderAdditionalStuffDTOList.add(orderAdditionalStuffDTO3);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO4 = new OrderAdditionalStuffDTO("Barbeque chicken", "Non-­Veg Toppings", 40.0, 5);
		orderAdditionalStuffDTOList.add(orderAdditionalStuffDTO4);

		OrderPizzaDTO orderPizzaDTO1 = new OrderPizzaDTO("Deluxe Veggie", PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(), "Regular", 150.00, "New hand tossed",
				orderAdditionalStuffDTOList);
		orderPizzaDTO1.setOrderAdditionalStuffList(orderAdditionalStuffDTOList);
		//First Pizza ends

		orderPizzaDTOList.add(orderPizzaDTO1);
		return orderPizzaDTOList;
	}
}
