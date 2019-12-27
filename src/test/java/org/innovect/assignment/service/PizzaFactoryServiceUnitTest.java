package org.innovect.assignment.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.AppRunner;
import org.innovect.assignment.data.AdditionalStuffInfoListData;
import org.innovect.assignment.data.LargePizzaTwoToppingsNoCostOrderData;
import org.innovect.assignment.data.NonVegPizzaWithTwoNonVegToppingsOrderData;
import org.innovect.assignment.data.NormalOrderData;
import org.innovect.assignment.data.OutOfStockAdditionalStuffOrderData;
import org.innovect.assignment.data.OutOfStockPizzaOrderData;
import org.innovect.assignment.data.OutOfStockSidesOrderData;
import org.innovect.assignment.data.PizzaInfoListData;
import org.innovect.assignment.data.RegularAndMediumPizzaOrderData;
import org.innovect.assignment.data.VegPizzaNonVegToppingsOrderData;
import org.innovect.assignment.data.ZeroAdditionalStuffOrderData;
import org.innovect.assignment.data.ZeroSidesOrderData;
import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.dto.CustomerDashboardInfoDTO;
import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.dto.SubmitOrderPostDTO;
import org.innovect.assignment.model.AdditionalStuffCategoryEnum;
import org.innovect.assignment.model.AdditionalStuffInfo;
import org.innovect.assignment.model.Order;
import org.innovect.assignment.model.PizzaInfo;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;
import org.innovect.assignment.repository.AdditionalStuffRepository;
import org.innovect.assignment.repository.CustomRepository;
import org.innovect.assignment.repository.OrderRepository;
import org.innovect.assignment.repository.PizzaInfoRepository;
import org.innovect.assignment.utils.PizzaShopUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class contains detailed Unit test cases for various business rules mentioned in
 * problem statement.
 * @author Ashutosh Shukla
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRunner.class)
public class PizzaFactoryServiceUnitTest {

	@TestConfiguration
	static class GenericTestContextConfiguration {
		@Bean
		public PizzaFactory getPizzaFactory() {
			return new PizzaFactoryService();
		}
	}

	@Autowired
	private PizzaFactory pizzaFactory;

	@MockBean
	private PizzaInfoRepository pizzaInfoRepository;

	@MockBean
	private AdditionalStuffRepository additionalStuffRepository;

	@MockBean
	private OrderRepository orderRepository;

	@MockBean
	private CustomRepository customRepository;
	
	/**
	 * Fetch all information for displaying on Self Service Terminal
	 */
	@Test
	public void getAvailableOptionForPizzaTest() {
		when(pizzaInfoRepository.findAll()).thenReturn(PizzaInfoListData.createPizzaInfoList());
		when(additionalStuffRepository.findAll()).thenReturn(AdditionalStuffInfoListData.createAdditionalStuffInfoList());
		
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
		when(customRepository.getPizzaAvailability()).thenReturn(new ArrayList<String>());
		when(customRepository.getAdditionalStuffAvailability()).thenReturn(new ArrayList<String>());
		
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
		when(customRepository.getPizzaAvailability()).thenReturn(null);
		when(customRepository.getAdditionalStuffAvailability()).thenReturn(new ArrayList<String>());
		
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
		when(customRepository.getPizzaAvailability()).thenReturn(new ArrayList<String>());
		when(customRepository.getAdditionalStuffAvailability()).thenReturn(new ArrayList<String>());
		
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

		PizzaInfo pizzaInfo = new PizzaInfo("Deluxe Veggie", PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(),
				"Regular", 150.00, 20);
		when(pizzaInfoRepository.findByPizzaNameAndPizzaSize("Deluxe Veggie", "Regular")).thenReturn(pizzaInfo);

		String response = pizzaFactory.submitOrder(submitOrderPostDTO);
		Assert.assertEquals(response, PizzaShopUtils.SUCCESSFUL_ORDER_SUBMIT_OPERATION);
	}

	/**
	 * Add Pizza to inventory
	 */
	@Test
	public void addPizzaInfoTest() {
		List<PizzaInfoDTO> pizzaInfoList = new ArrayList<>();
		PizzaInfoDTO pizzaInfoDTO = new PizzaInfoDTO(0, "Deluxe Veggie",
				PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(), "Regular", 1001.00, 20);
		pizzaInfoList.add(pizzaInfoDTO);
		String response = pizzaFactory.addPizzaInfoInventory(pizzaInfoList);
		Assert.assertEquals(response, PizzaShopUtils.SUCCESSFUL_OPERATION);

	}

	/**
	 * Update Pizza information present in inventory.
	 */
	@Test
	public void updatePizzaInfoTest() {
		List<PizzaInfoDTO> pizzaInfoList = new ArrayList<>();
		PizzaInfoDTO pizzaInfoDTO = new PizzaInfoDTO(0, "Test Pizza", PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(),
				"Regular", 505.00, 10);
		pizzaInfoList.add(pizzaInfoDTO);

		PizzaInfo pizzaInfo = new PizzaInfo("Test Pizza", PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(),
				"Regular", 150.00, 10);
		when(pizzaInfoRepository.findByPizzaNameAndPizzaSize("Test Pizza", "Regular")).thenReturn(pizzaInfo);
		String response = pizzaFactory.addPizzaInfoInventory(pizzaInfoList);
		Assert.assertEquals(response, PizzaShopUtils.SUCCESSFUL_OPERATION);
	}

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
		String response = pizzaFactory.addAdditionalStuffInventory(additionalStuffDTOList);
		Assert.assertEquals(response, PizzaShopUtils.SUCCESSFUL_OPERATION);

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
		String response = pizzaFactory.addAdditionalStuffInventory(additionalStuffDTOList);
		Assert.assertEquals(response, PizzaShopUtils.SUCCESSFUL_OPERATION);

	}

	/**
	 * Vegetarian pizza cannot have a non-­vegetarian topping.
	 */
	@Test(expected = RuntimeException.class)
	public void VegPizzaNonVegToppingsTest() {
		SubmitOrderPostDTO submitOrderPostDTO = VegPizzaNonVegToppingsOrderData.createSubmitOrderPostDTOObject();
		when(customRepository.getPizzaAvailability()).thenReturn(new ArrayList<String>());
		when(customRepository.getAdditionalStuffAvailability()).thenReturn(new ArrayList<String>());
		
		Order verifiedOrder = pizzaFactory.verifyOrder(submitOrderPostDTO);
		Assert.assertNotNull(verifiedOrder);
		Assert.assertEquals(2540.00, verifiedOrder.getTotalAmountToPay(), 0.0);
	}

	/*
	 * Non-­vegetarian pizza cannot have paneer topping
	 */
	@Test(expected = RuntimeException.class)
	public void NonVegPizzaPaneerToppingsTest() {
		SubmitOrderPostDTO submitOrderPostDTO = VegPizzaNonVegToppingsOrderData.createSubmitOrderPostDTOObject();
		when(customRepository.getPizzaAvailability()).thenReturn(new ArrayList<String>());
		when(customRepository.getAdditionalStuffAvailability()).thenReturn(new ArrayList<String>());

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
		when(customRepository.getPizzaAvailability()).thenReturn(new ArrayList<String>());
		when(customRepository.getAdditionalStuffAvailability()).thenReturn(new ArrayList<String>());

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
		when(customRepository.getPizzaAvailability()).thenReturn(new ArrayList<String>());
		when(customRepository.getAdditionalStuffAvailability()).thenReturn(new ArrayList<String>());

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
		when(customRepository.getPizzaAvailability()).thenReturn(new ArrayList<String>());
		when(customRepository.getAdditionalStuffAvailability()).thenReturn(new ArrayList<String>());

		String response = pizzaFactory.submitOrder(submitOrderPostDTO);
		Assert.assertNotNull(response, PizzaShopUtils.SUCCESSFUL_ORDER_SUBMIT_OPERATION);
	}

	/*
	 * Order with no Sides can be ordered.
	 */
	@Test
	public void zeroSidesOrderTest() {
		SubmitOrderPostDTO submitOrderPostDTO = ZeroSidesOrderData.createSubmitOrderPostDTOObject();
		when(customRepository.getPizzaAvailability()).thenReturn(new ArrayList<String>());
		when(customRepository.getAdditionalStuffAvailability()).thenReturn(new ArrayList<String>());

		String response = pizzaFactory.submitOrder(submitOrderPostDTO);
		Assert.assertNotNull(response, PizzaShopUtils.SUCCESSFUL_ORDER_SUBMIT_OPERATION);
	}

	/**
	 * Out of Stock pizza can not be ordered.
	 */
	@Test(expected = RuntimeException.class)
	public void outOfStockPizzaTest() {
		List<String> unAvailablePizzaList = new ArrayList<>();
		unAvailablePizzaList.add("Deluxe Veggie");
		when(customRepository.getPizzaAvailability()).thenReturn(unAvailablePizzaList);
		when(customRepository.getAdditionalStuffAvailability()).thenReturn(new ArrayList<String>());

		SubmitOrderPostDTO submitOrderPostDTO = OutOfStockPizzaOrderData.createSubmitOrderPostDTOObject();
		String response = pizzaFactory.submitOrder(submitOrderPostDTO);
		Assert.assertEquals(response, PizzaShopUtils.SUCCESSFUL_ORDER_SUBMIT_OPERATION);

	}

	/**
	 * Out of Stock additional stuff can not be added to ordered Pizza.
	 */
	@Test(expected = RuntimeException.class)
	public void outOfStockAdditionalStuffTest() {
		List<String> unavailablStuffList = new ArrayList<>();
		unavailablStuffList.add("Out of Stock Stuff");
		when(customRepository.getPizzaAvailability()).thenReturn(new ArrayList<String>());
		when(customRepository.getAdditionalStuffAvailability()).thenReturn(unavailablStuffList);

		SubmitOrderPostDTO submitOrderPostDTO = OutOfStockAdditionalStuffOrderData.createSubmitOrderPostDTOObject();
		String response = pizzaFactory.submitOrder(submitOrderPostDTO);
		Assert.assertEquals(response, PizzaShopUtils.SUCCESSFUL_ORDER_SUBMIT_OPERATION);

	}

	/*
	 * Sides which are out of stock can not be added to Pizza.
	 */
	@Test(expected = RuntimeException.class)
	public void outOfStockSidesTest() {
		List<String> unavailablStuffList = new ArrayList<>();
		unavailablStuffList.add("Out of Stock Sides");
		when(customRepository.getPizzaAvailability()).thenReturn(new ArrayList<String>());
		when(customRepository.getAdditionalStuffAvailability()).thenReturn(unavailablStuffList);

		SubmitOrderPostDTO submitOrderPostDTO = OutOfStockSidesOrderData.createSubmitOrderPostDTOObject();
		String response = pizzaFactory.submitOrder(submitOrderPostDTO);
		Assert.assertEquals(response, PizzaShopUtils.SUCCESSFUL_ORDER_SUBMIT_OPERATION);
	}

}
