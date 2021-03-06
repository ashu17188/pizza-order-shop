package org.innovect.assignment.service;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.AppRunner;
import org.innovect.assignment.data.LargePizzaTwoToppingsNoCostOrderData;
import org.innovect.assignment.data.MoreThanOneCrustLargeOrderData;
import org.innovect.assignment.data.MoreThanOneCrustMediumOrderData;
import org.innovect.assignment.data.MoreThanOneCrustRegularOrderData;
import org.innovect.assignment.data.NonVegPizzaPaneerToppingsOrderData;
import org.innovect.assignment.data.NonVegPizzaWithTwoNonVegToppingsOrderData;
import org.innovect.assignment.data.NonVegWithPaneerRegular;
import org.innovect.assignment.data.NormalOrderData;
import org.innovect.assignment.data.OutOfStockAdditionalStuffOrderData;
import org.innovect.assignment.data.OutOfStockPizzaOrderData;
import org.innovect.assignment.data.OutOfStockSidesOrderData;
import org.innovect.assignment.data.RegularAndMediumPizzaOrderData;
import org.innovect.assignment.data.VegPizzaNonVegToppingsOrderData;
import org.innovect.assignment.data.VegWithNonVegToppingsMediumOrder;
import org.innovect.assignment.data.VegWithNonVegToppingsRegularOrder;
import org.innovect.assignment.data.ZeroAdditionalStuffOrderData;
import org.innovect.assignment.data.ZeroSidesOrderData;
import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.dto.CustomerDashboardInfoDTO;
import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.model.AdditionalStuffCategoryEnum;
import org.innovect.assignment.model.Order;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;
import org.innovect.assignment.utils.PizzaShopConstants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class contains Integration tests for various scenarios mentioned in
 * business rules of problem statement.
 * 
 * @author Ashutosh Shukla
 *
 */
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

	@Autowired
	private PizzaInventory pizzaInventory;

	@Autowired
	private IngredientInventory ingredientInventory;

	/**
	 * Fetch all information for displaying on Self Service Terminal
	 */
	@Test
	public void getAvailableOptionForPizzaTest() {
		CustomerDashboardInfoDTO customerDashboardInfoDTO = pizzaFactory.getPizzaAndExtraInfo();
		Assert.assertNotNull(customerDashboardInfoDTO);
		Assert.assertNotEquals(customerDashboardInfoDTO.getAdditionalStuffDTOList().size(), 0);
	}

	/**
	 * Test if order contains valid number of Pizza, Additional Stuff, Sides,
	 * calculate total order amount before Submit Order.
	 */
	@Test
	public void verifyOrderTest() {
		SubmitOrderPostDTO submitOrderPostDTO = NormalOrderData.createSubmitOrderPostDTOObject();
		Order verifiedOrder = pizzaFactory.verifyOrder(submitOrderPostDTO);
		Assert.assertNotNull(verifiedOrder);
		Assert.assertEquals(verifiedOrder.getTotalAmountToPay(), 2325.00, 0.0);
	}

	/*
	 * Test if Large size Pizza order contains valid number of Pizza, Additional
	 * Stuff, Sides, calculate total order amount before Submit Order.
	 */
	@Test
	public void verifyOrderAndTotalAmountLargePizzaTest() {
		SubmitOrderPostDTO submitOrderPostDTO = NormalOrderData.createSubmitOrderPostDTOObject();
		Order verifiedOrder = pizzaFactory.verifyOrder(submitOrderPostDTO);
		Assert.assertNotNull(verifiedOrder);
		Assert.assertEquals(verifiedOrder.getTotalAmountToPay(), 2325.00, 0.0);
	}

	/*
	 * Test if Regular and Medium size Pizza order contains valid number of Pizza,
	 * Additional Stuff, Sides, calculate total order amount before Submit Order.
	 */
	@Test
	public void verifyOrderAndTotalAmountRegularMediumPizzaTest() {
		SubmitOrderPostDTO submitOrderPostDTO = RegularAndMediumPizzaOrderData.createSubmitOrderPostDTOObject();
		Order verifiedOrder = pizzaFactory.verifyOrder(submitOrderPostDTO);
		Assert.assertNotNull(verifiedOrder);

	}

	/**
	 * Submit order having everything according to given business rule.
	 */
	@Test
	public void submitNormalOrderTest() {
		SubmitOrderPostDTO submitOrderPostDTO = NormalOrderData.createSubmitOrderPostDTOObject();
		SubmitOrderPostDTO response = pizzaFactory.submitOrder(submitOrderPostDTO);

		Assert.assertNotNull(response.getOrderId());
	}

	/**
	 * Vegetarian pizza cannot have a non-­vegetarian topping.
	 */
	@Test(expected = RuntimeException.class)
	public void VegPizzaNonVegToppingsTest() {
		SubmitOrderPostDTO submitOrderPostDTO = VegPizzaNonVegToppingsOrderData.createSubmitOrderPostDTOObject();
		Order verifiedOrder = pizzaFactory.verifyOrder(submitOrderPostDTO);
		Assert.assertNotNull(verifiedOrder);
		Assert.assertEquals(2540.00, verifiedOrder.getTotalAmountToPay(), 0.0);
	}

	/*
	 * Non-­vegetarian pizza cannot have paneer topping
	 */
	@Test(expected = RuntimeException.class)
	public void NonVegPizzaPaneerToppingsTest() {
		SubmitOrderPostDTO submitOrderPostDTO = NonVegPizzaPaneerToppingsOrderData.createSubmitOrderPostDTOObject();
		Order verifiedOrder = pizzaFactory.verifyOrder(submitOrderPostDTO);
		Assert.assertNotNull(verifiedOrder);
	}

	/**
	 * You can add only one of the non-­veg toppings in non-­vegetarian pizza.
	 */
	@Test(expected = RuntimeException.class)
	public void nonVegPizzaWithTwoNonVegToppings() {
		SubmitOrderPostDTO submitOrderPostDTO = NonVegPizzaWithTwoNonVegToppingsOrderData
				.createSubmitOrderPostDTOObject();
		Order verifiedOrder = pizzaFactory.verifyOrder(submitOrderPostDTO);
		Assert.assertNotNull(verifiedOrder);

	}

	/**
	 * Large size pizzas come with any 2 toppings of customers choice with no
	 * additional cost
	 */
	@Test
	public void LargePizzaTwoToppingsNoCostTest() {
		SubmitOrderPostDTO submitOrderPostDTO = LargePizzaTwoToppingsNoCostOrderData.createSubmitOrderPostDTOObject();
		Order verifiedOrder = pizzaFactory.verifyOrder(submitOrderPostDTO);
		Assert.assertNotNull(verifiedOrder);
		Assert.assertEquals(verifiedOrder.getTotalAmountToPay(), 2325.00, 0.0);
	}

	/**
	 * No Pizza with Sides is not allowed
	 */
	@Test(expected = RuntimeException.class)
	public void NoPizzaWithSidesVerifyOrderTest() {
		SubmitOrderPostDTO submitOrderPostDTO = NormalOrderData.createSubmitOrderPostDTOObject();
		submitOrderPostDTO.setOrderPizzaDTOList(null);
		Order verifiedOrder = pizzaFactory.verifyOrder(submitOrderPostDTO);
		Assert.assertNotNull(verifiedOrder);
	}

	/*
	 * Order with Pizzas having no Additional Stuff can be ordered.
	 */
	@Test
	public void zeroAdditionalStuffOrderTest() {
		SubmitOrderPostDTO submitOrderPostDTO = ZeroAdditionalStuffOrderData.createSubmitOrderPostDTOObject();
		SubmitOrderPostDTO response = pizzaFactory.submitOrder(submitOrderPostDTO);
		Assert.assertNotNull(response.getOrderId());
	}

	/*
	 * Order with no Sides can be ordered.
	 */
	@Test
	public void zeroSidesOrderTest() {
		SubmitOrderPostDTO submitOrderPostDTO = ZeroSidesOrderData.createSubmitOrderPostDTOObject();
		SubmitOrderPostDTO response = pizzaFactory.submitOrder(submitOrderPostDTO);
		Assert.assertNotNull(response.getOrderId());
	}

	/**
	 * Out of Stock pizza can not be ordered.
	 */
	@Test(expected = RuntimeException.class)
	public void outOfStockPizzaTest() {
		List<PizzaInfoDTO> pizzaInfoList = new ArrayList<>();
		PizzaInfoDTO pizzaInfoDTO = new PizzaInfoDTO(0, "Out of Stock Pizza",
				PizzaInfoCategoryEnum.NON_VEGETARIAN.toString(), "Large", 505.00, 0);
		pizzaInfoList.add(pizzaInfoDTO);
		String responsePizzaAddition = pizzaInventory.addAndUpdatePizzaBatch(pizzaInfoList);
		Assert.assertEquals(responsePizzaAddition, PizzaShopConstants.SUCCESSFUL_OPERATION);

		SubmitOrderPostDTO submitOrderPostDTO = OutOfStockPizzaOrderData.createSubmitOrderPostDTOObject();
		SubmitOrderPostDTO response = pizzaFactory.submitOrder(submitOrderPostDTO);
		Assert.assertEquals(submitOrderPostDTO, response);

	}

	/**
	 * Out of Stock additional stuff can not be added to ordered Pizza.
	 */
	@Test(expected = RuntimeException.class)
	public void outOfStockAdditionalStuffTest() {
		List<AdditionalStuffInfoDTO> additionalStuffDTOList = new ArrayList<>();
		AdditionalStuffInfoDTO additionalStuffInfoDTO = new AdditionalStuffInfoDTO("Out of Stock Stuff",
				AdditionalStuffCategoryEnum.VEG_TOPPINGS.getCategory(), 39.00, 0);
		additionalStuffDTOList.add(additionalStuffInfoDTO);
		String stuffAdditionResponse = ingredientInventory.saveAndUpdateIngredientBatch(additionalStuffDTOList);
		Assert.assertEquals(stuffAdditionResponse, PizzaShopConstants.SUCCESSFUL_OPERATION);

		SubmitOrderPostDTO submitOrderPostDTO = OutOfStockAdditionalStuffOrderData.createSubmitOrderPostDTOObject();
		pizzaFactory.submitOrder(submitOrderPostDTO);
	}

	/*
	 * Sides which are out of stock can not be added to Pizza.
	 */
	@Test(expected = RuntimeException.class)
	public void outOfStockSidesTest() {
		List<AdditionalStuffInfoDTO> additionalStuffDTOList = new ArrayList<>();
		AdditionalStuffInfoDTO additionalStuffInfoDTO = new AdditionalStuffInfoDTO("Out of Stock Sides",
				AdditionalStuffCategoryEnum.SIDES.getCategory(), 50.00, 0);
		additionalStuffDTOList.add(additionalStuffInfoDTO);
		String stuffAdditionResponse = ingredientInventory.saveAndUpdateIngredientBatch(additionalStuffDTOList);
		Assert.assertEquals(stuffAdditionResponse, PizzaShopConstants.SUCCESSFUL_OPERATION);

		SubmitOrderPostDTO submitOrderPostDTO = OutOfStockSidesOrderData.createSubmitOrderPostDTOObject();
		pizzaFactory.submitOrder(submitOrderPostDTO);
	}

	/**
	 * Submit order which does not have any order
	 */
	@Test(expected = RuntimeException.class)
	public void submitNullOrderTest() {
		 pizzaFactory.submitOrder(null);
	}

	/**
	 * This test verifies whether Regular Pizza contains more than one crust. Data contains Pizza with more that
	 * 1 Crust.
	 */
	@Test(expected = RuntimeException.class)
	public void verifyMoreThanOneCrustRegularTest() {
		SubmitOrderPostDTO submitOrderPostDTO = MoreThanOneCrustRegularOrderData.createSubmitOrderPostDTOObject();
		pizzaFactory.verifyOrder(submitOrderPostDTO);
	}

	/**
	 * This test verifies whether Medium Pizza contains more than one crust. Data contains Pizza with more that
	 * 1 Crust.
	 */
	@Test(expected = RuntimeException.class)
	public void verifyMoreThanOneCrustMediumTest() {
		SubmitOrderPostDTO submitOrderPostDTO = MoreThanOneCrustMediumOrderData.createSubmitOrderPostDTOObject();
		pizzaFactory.verifyOrder(submitOrderPostDTO);
	}

	/**
	 * This test verifies whether Large Pizza contains more than one crust. Data contains Pizza with more that
	 * 1 Crust.
	 */
	@Test(expected = RuntimeException.class)
	public void verifyMoreThanOneCrustLargeTest() {
		SubmitOrderPostDTO submitOrderPostDTO = MoreThanOneCrustLargeOrderData.createSubmitOrderPostDTOObject();
		pizzaFactory.verifyOrder(submitOrderPostDTO);
	}
	

	/**
	 * Submit order having everything according to given business rule.
	 */
	@Test
	public void getByOrderIdNormalOrderTest() {
		SubmitOrderPostDTO submitOrderPostDTO = NormalOrderData.createSubmitOrderPostDTOObject();
		SubmitOrderPostDTO response = pizzaFactory.submitOrder(submitOrderPostDTO);
		SubmitOrderPostDTO objFetchFromDB = pizzaFactory.getOrderById(response.getOrderId());
		Assert.assertEquals(response.getOrderId(), objFetchFromDB.getOrderId());
	}

	/**
	 * This test Medium Non Veg pizza does not have Paneer toppings.
	 */
	@Test(expected = RuntimeException.class)
	public void nonVegRegularTest() {
		SubmitOrderPostDTO submitOrderPostDTO = NonVegWithPaneerRegular.createSubmitOrderPostDTOObject();
		pizzaFactory.verifyOrder(submitOrderPostDTO);
	}

	/**
	 * This test Regular Veg pizza with non veg toppings.
	 */
	@Test(expected = RuntimeException.class)
	public void VegRegularWithNonVegToppingsTest() {
		SubmitOrderPostDTO submitOrderPostDTO = VegWithNonVegToppingsRegularOrder.createSubmitOrderPostDTOObject();
		pizzaFactory.verifyOrder(submitOrderPostDTO);
	}

	/**
	 * This test Medium Veg pizza with non veg toppings.
	 */
	@Test(expected = RuntimeException.class)
	public void VegMediumWithNonVegToppingsTest() {
		SubmitOrderPostDTO submitOrderPostDTO = VegWithNonVegToppingsMediumOrder.createSubmitOrderPostDTOObject();
		pizzaFactory.verifyOrder(submitOrderPostDTO);
	}

}
