package org.innovect.assignment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.data.NormalOrderData;
import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.model.AdditionalStuffCategoryEnum;
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

/**
 * This class contains basic test cases. For detailed test scenarios and
 * business rules validation, please visits PizzaFactoryServiceTest.java
 * 
 * @author Ashutosh Shukla
 */
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
	public void getAvailableOptionForPizzaTest() throws Exception {
		mockMvc.perform(get("/api/dashboard").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

	@Test
	public void verifyOrderTest() throws Exception {
		String payload = new Gson().toJson(NormalOrderData.createSubmitOrderPostDTOObject());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/verifyOrder").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void submitNormalOrderTest() throws Exception {
		String payload = new Gson().toJson(NormalOrderData.createSubmitOrderPostDTOObject());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/submitOrder").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	public void addPizzaInfoTest() throws Exception {
		List<PizzaInfoDTO> pizzaInfoList = new ArrayList<>();
		PizzaInfoDTO pizzaInfoDTO = new PizzaInfoDTO(0, "Test Pizza", PizzaInfoCategoryEnum.VEGETARIAN_PIZZA.getCategory().toString(),
				"Regular", 505.00, 10);
		pizzaInfoList.add(pizzaInfoDTO);

		String payload = new Gson().toJson(pizzaInfoList);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/addUpdatePizza").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void addAdditionalStuffTest() throws Exception {
		List<AdditionalStuffInfoDTO> additionalStuffDTOList = new ArrayList<>();
		AdditionalStuffInfoDTO additionalStuffInfoDTO = new AdditionalStuffInfoDTO("Test Stuff",
				AdditionalStuffCategoryEnum.VEG_TOPPINGS.getCategory(), 39.00, 50);
		additionalStuffDTOList.add(additionalStuffInfoDTO);

		String payload = new Gson().toJson(additionalStuffDTOList);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/addUpdateStuff").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void getAllPizzInfoListTest() throws Exception {
		mockMvc.perform(get("/api/allPizzaInfo").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

	@Test
	public void getAllStuffInfoTest() throws Exception {
		mockMvc.perform(get("/api/allStuffInfo").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

}
