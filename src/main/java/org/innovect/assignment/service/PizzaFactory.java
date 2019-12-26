package org.innovect.assignment.service;

import java.util.List;

import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.dto.CustomerDashboardInfoDTO;
import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.model.Order;

/**
 * @author Ashutosh Shukla
 * 
 */
public interface PizzaFactory {

	/**
	 * @return Pizza, Toppings, Crush etc information to display on Self Service Terminal.
	 */
	CustomerDashboardInfoDTO getPizzaAndExtraInfo();

	/**
	 * This method verifies whether List of Pizza, Toppings, Crush, Sides are valid as per gives
	 * business rules.
	 * @param submitOrderPostDTO DTO containing list of Pizza and sides for validation.
	 * @return Order object if current operation is successfull otherwise throwing exception.
	 */
	Order verifyOrder(SubmitOrderPostDTO submitOrderPostDTO);

	/**
	 * This method saves Order after validation
	 * @param submitOrderPostDTO DTO containing list of Pizza and sides for validation.
	 * @return Confirmation String if current operation is successfull otherwise throwing exception.
	 */
	String submitOrder(SubmitOrderPostDTO submitOrderPostDTO);

	/**
	 * This method adds List of Pizza's in inventory
	 * @param pizzaInfoDTOList Pizza List to be added.
	 * @return 
	 */
	String addPizzaInfoInventory(List<PizzaInfoDTO> pizzaInfoDTOList);

	/**
	 * This method add additional Stuff to inventory like Toppings, Sides and update price and stock
	 * quantity of existing stuff.
	 * @param additionalStuffInfoDTOList
	 * @return response if operation is successful
	 */
	String addAdditionalStuffInventory(List<AdditionalStuffInfoDTO> additionalStuffInfoDTOList);

	/**
	 * @return All Pizza from PizzaInfo table
	 */
	List<PizzaInfoDTO> getAllPizzInfoList();

	/**
	 * @return Additional Stuff Information for Pizza Shop
	 */
	List<AdditionalStuffInfoDTO> getAllStuffInfo();

	
}
