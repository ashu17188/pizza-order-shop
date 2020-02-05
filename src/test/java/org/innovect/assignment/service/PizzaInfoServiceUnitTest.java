package org.innovect.assignment.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.AppRunner;
import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.model.PizzaInfo;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;
import org.innovect.assignment.repository.AdditionalStuffRepository;
import org.innovect.assignment.repository.CustomRepository;
import org.innovect.assignment.repository.OrderRepository;
import org.innovect.assignment.repository.PizzaInfoRepository;
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

import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppRunner.class)
public class PizzaInfoServiceUnitTest {

	@TestConfiguration
	static class GenericTestContextConfiguration {
		@Bean
		public PizzaInventory getPizzaInventory() {
			return new PizzaInfoService();
		}
	}

	@Autowired
	private PizzaInventory pizzaInventory;

	@MockBean
	private PizzaInfoRepository pizzaInfoRepository;

	@MockBean
	private AdditionalStuffRepository additionalStuffRepository;

	@MockBean
	private OrderRepository orderRepository;

	@MockBean
	private CustomRepository customRepository;

	/**
	 * Test if all pizzas can be fetched.
	 */
	@Test
	public void getAllPizzaTest() {
		List<PizzaInfoDTO> list = new ArrayList<>();
		list.add(new PizzaInfoDTO(0,"Test1","Vegetarian","Regular",100.00,50));
		list.add(new PizzaInfoDTO(0,"Test2","Non Vegetarian","Large",200.00,50));
		when(pizzaInventory.getAllPizza()).thenReturn(list);
		Assert.assertNotNull(pizzaInventory.getAllPizza());
	}

	/**
	 * Add Pizza to inventory, no exception should be thrown.
	 */
	@Test
	public void addOnePizzaInfoTest() {
		PizzaInfoDTO pizzaDTO = new PizzaInfoDTO(0, "Test1", "Vegetarian", "Regular", 100.00, 50);
		PizzaInfo objFromDB  = new Gson().fromJson(new Gson().toJson(pizzaDTO),PizzaInfo.class);
		when(pizzaInfoRepository.save(new Gson().fromJson(new Gson().toJson(pizzaDTO), PizzaInfo.class))).thenReturn(objFromDB);
		pizzaInventory.saveAndUpdatePizza(pizzaDTO);
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
		String response = pizzaInventory.addAndUpdatePizzaBatch(pizzaInfoList);
		Assert.assertEquals(response, PizzaShopConstants.SUCCESSFUL_OPERATION);

	}
	
	/**
	 * Add Pizza information present in inventory.
	 */
	@Test
	public void addPizzaInfoListBatchTest() {
		List<PizzaInfoDTO> pizzaInfoList = new ArrayList<>();
		PizzaInfoDTO pizzaInfoDTO = new PizzaInfoDTO(0, "Test Pizza1",
				PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(), "Regular", 505.00, 10);
		pizzaInfoList.add(pizzaInfoDTO);

		PizzaInfo pizzaInfo = new PizzaInfo("Test Pizza1", PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(),
				"Regular", 150.00, 10);
		when(pizzaInfoRepository.findByPizzaNameAndPizzaSize("Test Pizza", "Regular")).thenReturn(pizzaInfo);
		String response = pizzaInventory.addAndUpdatePizzaBatch(pizzaInfoList);
		Assert.assertEquals(response, PizzaShopConstants.SUCCESSFUL_OPERATION);
	}
	
	/**
	 * Update Pizza information present in inventory.
	 */
	@Test
	public void updatePizzaInfoTest() {
		List<PizzaInfoDTO> pizzaInfoList = new ArrayList<>();
		PizzaInfoDTO pizzaInfoDTO1 = new PizzaInfoDTO(0, "Test Pizza1",
				PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(), "Regular", 505.00, 10);
		PizzaInfoDTO pizzaInfoDTO2 = new PizzaInfoDTO(0, "Test Pizza2",
				PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(), "Large", 510.00, 20);
		pizzaInfoList.add(pizzaInfoDTO1);
		pizzaInfoList.add(pizzaInfoDTO2);

		PizzaInfo pizzaInfo1 = new PizzaInfo("Test Pizza", PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(),
				"Regular", 150.00, 10);
		PizzaInfo pizzaInfo2 = new PizzaInfo("Test Pizza2",
				PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(), "Large", 510.00, 20);
		
		when(pizzaInfoRepository.findByPizzaNameAndPizzaSize("Test Pizza", "Regular")).thenReturn(pizzaInfo1);
		when(pizzaInfoRepository.findByPizzaNameAndPizzaSize("Test Pizza", "Large")).thenReturn(pizzaInfo2);
		String response = pizzaInventory.addAndUpdatePizzaBatch(pizzaInfoList);
		Assert.assertEquals(response, PizzaShopConstants.SUCCESSFUL_OPERATION);
	}
}
