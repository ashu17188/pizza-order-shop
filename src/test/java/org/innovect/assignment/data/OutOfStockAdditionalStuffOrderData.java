package org.innovect.assignment.data;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.dto.OrderAdditionalStuffDTO;
import org.innovect.assignment.dto.OrderPizzaDTO;
import org.innovect.assignment.dto.OrderSidesDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;

public class OutOfStockAdditionalStuffOrderData {

	/**
	 * @return Prebuilt SubmitOrderPostDTO Object for testing various scenarios
	 */
	public static SubmitOrderPostDTO createSubmitOrderPostDTOObject(){
		SubmitOrderPostDTO submitOrderPostDTO = new SubmitOrderPostDTO();
		submitOrderPostDTO.setCustName("David Johnson");
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
		
		OrderAdditionalStuffDTO orderAdditionalStuffDTO4 = new OrderAdditionalStuffDTO("Out of Stock Stuff", "Veg Toppings", 35.0, 5);
		orderAdditionalStuffDTOList.add(orderAdditionalStuffDTO4);

		OrderPizzaDTO orderPizzaDTO1 = new OrderPizzaDTO("Deluxe Veggie", PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(), "Regular", 150.00, "New hand tossed",
				orderAdditionalStuffDTOList);
		orderPizzaDTO1.setOrderAdditionalStuffList(orderAdditionalStuffDTOList);
		//First Pizza ends

		//Second Pizza starts 
		List<OrderAdditionalStuffDTO> orderAdditionalStuffDTOList2 = new ArrayList<>();
		OrderAdditionalStuffDTO orderAdditionalStuffDTO21 = new OrderAdditionalStuffDTO("Black olive", "Veg Toppings", 20.0, 9);
		orderAdditionalStuffDTOList2.add(orderAdditionalStuffDTO21);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO24 = new OrderAdditionalStuffDTO("Grilled chicken", "Non-­Veg Toppings", 40.0, 5);
		orderAdditionalStuffDTOList2.add(orderAdditionalStuffDTO24);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO25 = new OrderAdditionalStuffDTO("Barbeque chicken", "Non-­Veg Toppings", 40.0, 5);
		orderAdditionalStuffDTOList2.add(orderAdditionalStuffDTO25);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO23 = new OrderAdditionalStuffDTO("Extra cheese", "miscellaneous", 35.0, 5);
		orderAdditionalStuffDTOList2.add(orderAdditionalStuffDTO23);

		OrderPizzaDTO orderPizzaDTO2 = new OrderPizzaDTO("Non-­Veg Supreme", PizzaInfoCategoryEnum.NON_VEGETARIAN.getCategory(), "Large", 425.00, "New hand tossed",
				orderAdditionalStuffDTOList2);
		orderPizzaDTO2.setOrderAdditionalStuffList(orderAdditionalStuffDTOList2);
		//Second Pizza ends
		
		orderPizzaDTOList.add(orderPizzaDTO1);
		orderPizzaDTOList.add(orderPizzaDTO2);
		return orderPizzaDTOList;
	}
}
