package org.innovect.assignment.pizza_order_shop;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.AppRunner;
import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.dto.CustomerDashboardInfoDTO;
import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.model.AdditionalStuffCategoryEnum;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;
import org.innovect.assignment.service.PizzaFactory;
import org.innovect.assignment.service.PizzaFactoryService;
import org.innovect.assignment.utils.PizzaShopUtils;
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
public class PizzaFactoryServiceTest {
	
	
	@TestConfiguration
	static class GenericTestContextConfiguration {

		@Bean
		public PizzaFactory getPizzaFactory() {
			return new PizzaFactoryService();
		}
	}

	@Autowired
	private PizzaFactory pizzaFactory;

	@Test
	public void getAvailableOptionForPizza() {
		CustomerDashboardInfoDTO customerDashboardInfoDTO = pizzaFactory.getPizzaAndExtraInfo();
		Assert.assertNotNull(customerDashboardInfoDTO);
		Assert.assertNotEquals(customerDashboardInfoDTO.getAdditionalStuffDTOList().size(), 0);
	}
	
	@Test
	public void verifyOrderAndTotalAmountLargePizzaTest() {}
	
	@Test
	public void verifyOrderAndTotalAmountRegularMediumPizzaTest() {}
	
	@Test
	public void submitOrderTest() {}
	
	@Test
	public void addPizzaInfoTest() {
		List<PizzaInfoDTO> pizzaInfoList = new ArrayList<>();
		PizzaInfoDTO pizzaInfoDTO = new PizzaInfoDTO(0,"Test Pizza",PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.toString(),"Regular",505.00,10);
		pizzaInfoList.add(pizzaInfoDTO);
		String response = pizzaFactory.addPizzaInfoInventory(pizzaInfoList);
		Assert.assertEquals(response, PizzaShopUtils.SUCCESSFUL_OPERATION);

	}

	@Test
	public void updatePizzaInfoTest() {
		List<PizzaInfoDTO> pizzaInfoList = new ArrayList<>();
		PizzaInfoDTO pizzaInfoDTO = new PizzaInfoDTO(0,"Deluxe Veggie",PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.toString(),"Regular",1001.00,20);
		pizzaInfoList.add(pizzaInfoDTO);
		String response = pizzaFactory.addPizzaInfoInventory(pizzaInfoList);
		Assert.assertEquals(response, PizzaShopUtils.SUCCESSFUL_OPERATION);
	}

	@Test
	public void addAdditionalStuffTest() {
		List<AdditionalStuffInfoDTO> additionalStuffDTOList = new ArrayList<>();
		AdditionalStuffInfoDTO additionalStuffInfoDTO = new AdditionalStuffInfoDTO("Test Stuff",
				AdditionalStuffCategoryEnum.VEG_TOPPINGS.toString(), 39.00, 50);
		additionalStuffDTOList.add(additionalStuffInfoDTO);
		String response =pizzaFactory.addAdditionalStuffInventory(additionalStuffDTOList);
		Assert.assertEquals(response, PizzaShopUtils.SUCCESSFUL_OPERATION);
	}

	@Test
	public void updateAdditionalStuffTest() {
		List<AdditionalStuffInfoDTO> additionalStuffDTOList = new ArrayList<>();
		AdditionalStuffInfoDTO additionalStuffInfoDTO = new AdditionalStuffInfoDTO("Chicken tikka",
				AdditionalStuffCategoryEnum.NON_VEG_TOPPINGS.toString(), 40.00, 40);
		additionalStuffDTOList.add(additionalStuffInfoDTO);
		String response = pizzaFactory.addAdditionalStuffInventory(additionalStuffDTOList);
		Assert.assertEquals(response, PizzaShopUtils.SUCCESSFUL_OPERATION);
	}
	
	/**
	 * Vegetarian pizza cannot have a non-­vegetarian topping.
	 */
	@Test
	public void VegPizzaNonVegToppingsTest() {}

	/*
	 * Non-­vegetarian pizza cannot have paneer topping
	 */
	@Test
	public void NonVegPizzaPaneerToppingsTest() {}

	/*
	 * Only one type of crust can be selected for any pizza.
	 */
	@Test
	public void OnlyOneCrustPerPizzaTest() {}

	/**
	 * Large size pizzas come with any 2 toppings of customers choice with no additional cost
	 */
	@Test
	public void LargePizzaTwoToppingsNoCostTest() {}

}
