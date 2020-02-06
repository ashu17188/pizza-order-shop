package org.innovect.assignment.data;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.dto.OrderAdditionalStuffDTO;
import org.innovect.assignment.dto.OrderPizzaDTO;
import org.innovect.assignment.dto.OrderSidesDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;

public class MoreThanOneCrustLargeOrderData {

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
		
		//Third Pizza starts 
		List<OrderAdditionalStuffDTO> orderAdditionalStuffDTOList3 = new ArrayList<>();
		OrderAdditionalStuffDTO orderAdditionalStuffDTO31 = new OrderAdditionalStuffDTO("Black olive", "Veg Toppings", 20.0, 9);
		orderAdditionalStuffDTOList3.add(orderAdditionalStuffDTO31);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO34 = new OrderAdditionalStuffDTO("Grilled chicken", "Non-­Veg Toppings", 40.0, 5);
		orderAdditionalStuffDTOList3.add(orderAdditionalStuffDTO34);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO35 = new OrderAdditionalStuffDTO("Barbeque chicken", "Non-­Veg Toppings", 40.0, 5);
		orderAdditionalStuffDTOList3.add(orderAdditionalStuffDTO35);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO36 = new OrderAdditionalStuffDTO("Extra cheese", "miscellaneous", 35.0, 5);
		orderAdditionalStuffDTOList3.add(orderAdditionalStuffDTO36);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO37 = new OrderAdditionalStuffDTO("Cheese Burst", "crust", 0, 5);
		orderAdditionalStuffDTOList3.add(orderAdditionalStuffDTO37);

		OrderAdditionalStuffDTO orderAdditionalStuffDTO38 = new OrderAdditionalStuffDTO("New hand tossed", "crust", 0, 5);
		orderAdditionalStuffDTOList3.add(orderAdditionalStuffDTO38);
		
		OrderPizzaDTO orderPizzaDTO3 = new OrderPizzaDTO("Non-­Veg Supreme", PizzaInfoCategoryEnum.NON_VEGETARIAN.getCategory(), "Large", 425.00, "New hand tossed",
				orderAdditionalStuffDTOList3);
		orderPizzaDTO3.setOrderAdditionalStuffList(orderAdditionalStuffDTOList3);
		//Third Pizza ends


		orderPizzaDTOList.add(orderPizzaDTO3);

		return orderPizzaDTOList;
	}
}
