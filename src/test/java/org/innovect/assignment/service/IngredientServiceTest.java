package org.innovect.assignment.service;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.AppRunner;
import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.model.AdditionalStuffCategoryEnum;
import org.innovect.assignment.utils.PizzaShopConstants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRunner.class)
public class IngredientServiceTest {

	@TestConfiguration
	static class GenericTestContextConfiguration {
		@Bean
		public IngredientInventory getIngredientInventory() {
			return new IngredientService();
		}
	}

	@Autowired
	private IngredientInventory ingredientInventory;

	/**
	 * Test for fetching All additional Stuff from inventory.
	 */
	@Test
	public void getAllIngredientTest() {
		Assert.assertNotNull(ingredientInventory.getAllIngredient());
	}

	/**
	 * Add Addition Stuff of various category eg Veg Toppings, miscellaneous, sides
	 * etc.
	 */
	@Test
	public void addAdditionalStuffTest() {
		List<AdditionalStuffInfoDTO> additionalStuffDTOList = new ArrayList<>();
		AdditionalStuffInfoDTO additionalStuffInfoDTO = new AdditionalStuffInfoDTO("Test Stuff",
				AdditionalStuffCategoryEnum.VEG_TOPPINGS.getCategory(), 39.00, 50);
		additionalStuffDTOList.add(additionalStuffInfoDTO);
		String response = ingredientInventory.saveAndUpdateIngredientBatch(additionalStuffDTOList);
		Assert.assertEquals(response, PizzaShopConstants.SUCCESSFUL_OPERATION);
	}


	/**
	 * Update information of Additional Stuff (eg. Veg Toppings, sides etc) in
	 * inventory
	 */
	@Test
	public void updateAdditionalStuffTest() {
		List<AdditionalStuffInfoDTO> additionalStuffDTOList = new ArrayList<>();
		AdditionalStuffInfoDTO additionalStuffInfoDTO = new AdditionalStuffInfoDTO("Chicken tikka",
				AdditionalStuffCategoryEnum.NON_VEG_TOPPINGS.getCategory(), 40.00, 40);
		additionalStuffDTOList.add(additionalStuffInfoDTO);
		String response = ingredientInventory.saveAndUpdateIngredientBatch(additionalStuffDTOList);
		Assert.assertEquals(response, PizzaShopConstants.SUCCESSFUL_OPERATION);
	}

	/**
	 * Delete ingredient
	 */
	@Test
	public void deleteIngredientTest() {
		AdditionalStuffInfoDTO additionalStuffInfoDTO = new AdditionalStuffInfoDTO("Test Stuff2",
				AdditionalStuffCategoryEnum.VEG_TOPPINGS.getCategory(), 39.00, 50);
		ingredientInventory.saveAndUpdateIngredient(additionalStuffInfoDTO);
		ingredientInventory.deleteIngredient(additionalStuffInfoDTO.getStuffName());
		
	}
	
	/**
	 * Delete ingredient
	 */
	@Test(expected = RuntimeException.class)
	public void deleteNullIngredientTest() {
		ingredientInventory.deleteIngredient(null);
	}
	

	/**
	 *Save and update null for batch save and update.
	 */
	@Test(expected = RuntimeException.class)
	public void nullCheckAdditionalStuffTest() {
		ingredientInventory.saveAndUpdateIngredientBatch(null);
	}

	/*Adding Ingredient two times should not create two records.
	 */
	@Test
	public void addingSameIngredientTest(){
		AdditionalStuffInfoDTO additionalStuffInfoDTO1 = new AdditionalStuffInfoDTO("Chicken tikka",
				AdditionalStuffCategoryEnum.NON_VEG_TOPPINGS.getCategory(), 40.00, 50);
		ingredientInventory.saveAndUpdateIngredient(additionalStuffInfoDTO1);
		
		AdditionalStuffInfoDTO additionalStuffInfoDTO2 = new AdditionalStuffInfoDTO("Chicken tikka",
				AdditionalStuffCategoryEnum.NON_VEG_TOPPINGS.getCategory(), 40.00, 40);
		ingredientInventory.saveAndUpdateIngredient(additionalStuffInfoDTO2);

		AdditionalStuffInfoDTO objFromDB = ingredientInventory.getIngredientById("Chicken tikka");
		Assert.assertEquals(objFromDB.getPrice(), 40,0.0);
	}
	 
}
