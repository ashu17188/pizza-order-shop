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
	 * Update Pizza information present in inventory.
	 */
	@Test
	public void updatePizzaInfoTest() {
		List<PizzaInfoDTO> pizzaInfoList = new ArrayList<>();
		PizzaInfoDTO pizzaInfoDTO = new PizzaInfoDTO(0, "Test Pizza",
				PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(), "Regular", 505.00, 10);
		pizzaInfoList.add(pizzaInfoDTO);

		PizzaInfo pizzaInfo = new PizzaInfo("Test Pizza", PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory(),
				"Regular", 150.00, 10);
		when(pizzaInfoRepository.findByPizzaNameAndPizzaSize("Test Pizza", "Regular")).thenReturn(pizzaInfo);
		String response = pizzaInventory.addAndUpdatePizzaBatch(pizzaInfoList);
		Assert.assertEquals(response, PizzaShopConstants.SUCCESSFUL_OPERATION);
	}

}
