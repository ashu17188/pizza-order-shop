package org.innovect.assignment.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.model.PizzaInfoCategoryEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PizzaInfoControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private PizzaInfoController pizzaInfoController;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.pizzaInfoController).build();
	}

	@Test
	public void getAllPizzaTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/pizzas")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	
	@Test
	public void addPizzaTest() throws Exception {
		PizzaInfoDTO pizzaInfoDTO = new PizzaInfoDTO(0, null, PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory().toString(),
				"Regular", 505.00, 10);

		String payload = new Gson().toJson(pizzaInfoDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	public void addPizzaBatchTest() throws Exception {
		List<PizzaInfoDTO> pizzaInfoList = new ArrayList<>();
		PizzaInfoDTO pizzaInfoDTO = new PizzaInfoDTO(0, "Test Pizza", PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory().toString(),
				"Regular", 505.00, 10);
		pizzaInfoList.add(pizzaInfoDTO);

		String payload = new Gson().toJson(pizzaInfoList);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas/batch").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

}
