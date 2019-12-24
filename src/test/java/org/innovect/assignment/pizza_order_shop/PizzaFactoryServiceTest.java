package org.innovect.assignment.pizza_order_shop;

import org.innovect.assignment.AppRunner;
import org.innovect.assignment.dto.CustomerDashboardInfoDTO;
import org.innovect.assignment.service.PizzaFactory;
import org.innovect.assignment.service.PizzaFactoryService;
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
}
