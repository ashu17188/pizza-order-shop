package org.innovect.assignment.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.AppRunner;
import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.model.AdditionalStuffCategoryEnum;
import org.innovect.assignment.model.AdditionalStuffInfo;
import org.innovect.assignment.repository.AdditionalStuffRepository;
import org.innovect.assignment.utils.PizzaShopConstants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRunner.class)
public class IngredientServiceUnitTest {

	@TestConfiguration
	static class GenericTestContextConfiguration {
		@Bean
		public IngredientInventory getIngredientFactory() {
			return new IngredientService();
		}
	}

	@Autowired
	private IngredientInventory ingredientInventory;

	@MockBean
	private AdditionalStuffRepository additionalStuffRepository;


	/**
	 * Add Addition Stuff of various category eg Veg Toppings, miscellaneous, sides
	 * etc.
	 */
	@Test
	public void addAdditionalStuffTest() {
		List<AdditionalStuffInfoDTO> additionalStuffDTOList = new ArrayList<>();
		AdditionalStuffInfoDTO additionalStuffInfoDTO = new AdditionalStuffInfoDTO("Chicken tikka",
				AdditionalStuffCategoryEnum.NON_VEG_TOPPINGS.getCategory(), 40.00, 40);
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
		AdditionalStuffInfoDTO additionalStuffInfoDTO = new AdditionalStuffInfoDTO("Test Stuff",
				AdditionalStuffCategoryEnum.VEG_TOPPINGS.getCategory(), 39.00, 50);
		additionalStuffDTOList.add(additionalStuffInfoDTO);
		
		AdditionalStuffInfo stuff =new AdditionalStuffInfo("Test Stuff",AdditionalStuffCategoryEnum.VEG_TOPPINGS.getCategory(), 50.00, 100);
		when(additionalStuffRepository
					.findByStuffName(additionalStuffInfoDTO.getStuffName())).thenReturn(stuff);
		String response = ingredientInventory.saveAndUpdateIngredientBatch(additionalStuffDTOList);
		Assert.assertEquals(response, PizzaShopConstants.SUCCESSFUL_OPERATION);

	}

	
	
}