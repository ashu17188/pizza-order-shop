package org.innovect.assignment.service;

import org.innovect.assignment.dto.CustomerDashboardInfoDTO;
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

}
