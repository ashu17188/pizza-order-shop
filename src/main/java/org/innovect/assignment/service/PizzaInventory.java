package org.innovect.assignment.service;

import java.util.List;
import java.util.Optional;

import org.innovect.assignment.dto.PizzaInfoDTO;

/**
 * This class provides all information for Pizza from Inventory.
 * @author Ashutosh Shukla
 *
 */
public interface PizzaInventory {

	/**
	 * @return All pizza present in inventory.
	 */
	List<PizzaInfoDTO> getAllPizza();

	/**
	 * This method fetches pizza by unique pizza id.
	 * @param id unique id referring Pizza present in inventory.
	 * @return Object containing Pizza information.
	 */
	PizzaInfoDTO getPizzaById(int id);

	/**
	 * This methods save and update individual Pizza to inventory.
	 * @param pizzaInfoDTO
	 * @return Pizza which is saved to Database.
	 */
	PizzaInfoDTO saveAndUpdatePizza(PizzaInfoDTO pizzaInfoDTO);

	/**
	 * This method does the batch add and update for bulk processsing.
	 * @param pizzaInfoDTOList
	 * @return "SUCCESSFUL" or specific reason for failure.
	 */
	String addAndUpdatePizzaBatch(List<PizzaInfoDTO> pizzaInfoDTOList);

	void deletePizza(int pizzaId);

}
