package org.innovect.assignment.data;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.dto.OrderAdditionalStuffDTO;
import org.innovect.assignment.dto.OrderPizzaDTO;
import org.innovect.assignment.dto.OrderSidesDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;

public class MoreThanOneCrustMediumOrderData {

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

		//Second Pizza starts 
		List<OrderAdditionalStuffDTO> orderAdditionalStuffDTOList2 = new ArrayList<>();
		OrderAdditionalStuffDTO orderAdditionalStuffDTO21 = new OrderAdditionalStuffDTO("Black olive", "Veg Toppings", 20.0, 9);
		orderAdditionalStuffDTOList2.add(orderAdditionalStuffDTO21);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO24 = new OrderAdditionalStuffDTO("Grilled chicken", "Non-­Veg Toppings", 40.0, 5);
		orderAdditionalStuffDTOList2.add(orderAdditionalStuffDTO24);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO23 = new OrderAdditionalStuffDTO("Extra cheese", "miscellaneous", 35.0, 5);
		orderAdditionalStuffDTOList2.add(orderAdditionalStuffDTO23);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO26 = new OrderAdditionalStuffDTO("Cheese Burst", "crust", 0, 5);
		orderAdditionalStuffDTOList2.add(orderAdditionalStuffDTO26);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO27 = new OrderAdditionalStuffDTO("Fresh pan pizza", "crust", 0, 5);
		orderAdditionalStuffDTOList2.add(orderAdditionalStuffDTO27);
		
		OrderPizzaDTO orderPizzaDTO2 = new OrderPizzaDTO("Non-­Veg Supreme", PizzaInfoCategoryEnum.NON_VEGETARIAN.getCategory(), "Medium", 425.00, "New hand tossed",
				orderAdditionalStuffDTOList2);
		orderPizzaDTO2.setOrderAdditionalStuffList(orderAdditionalStuffDTOList2);
		//Second Pizza ends

		

		orderPizzaDTOList.add(orderPizzaDTO2);

		return orderPizzaDTOList;
	}
}
