package org.innovect.assignment.service;

import java.util.List;

import org.innovect.assignment.dto.AdditionalStuffInfoDTO;

public interface IngredientInventory {

	/**
	 * @return all ingredients from inventory.
	 */
	List<AdditionalStuffInfoDTO> getAllIngredient();
	
	/**
	 * This method fetches Ingredient from inventory using id.
	 * @param ingredientName Name of Sides, Crusts, Toppings.
	 * @return Ingredient object containing price, quantity.
	 */
	AdditionalStuffInfoDTO getIngredientById(String ingredientName);

	/**
	 * This method saves and update ingredient.
	 * @param additionalStuffInfoDTO Object needs to be updated or saved.
	 * @return saved Ingredient object.
	 */
	AdditionalStuffInfoDTO saveAndUpdateIngredient(AdditionalStuffInfoDTO additionalStuffInfoDTO);

	/**
	 * This method save and update list of ingredients in batch.
	 * @param Accept different types of ingredient
	 * @return "SUCCESSFUL" or specific error.
	 */
	String saveAndUpdateIngredientBatch(List<AdditionalStuffInfoDTO> additionalStuffInfoDTOList);

	/**
	 * This method deletes ingredient like sides, crust, toppings using name.
	 * @param name Unique name
	 */
	void deleteIngredient(String name);
}
