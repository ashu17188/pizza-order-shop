package org.innovect.assignment.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.data.PizzaInfoListData;
import org.innovect.assignment.dto.PizzaInfoDTO;
import org.innovect.assignment.service.PizzaInventory;
import org.innovect.assignment.utils.PizzaShopConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PizzaInfoControllerUnitTest {

	private MockMvc mockMvc;

	@Autowired
	private OrderProcessingController pizzaInfoController;

	@MockBean
	private PizzaInventory pizzaInventory;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.pizzaInfoController).build();
	}

	@Test
	public void addPizzaInfoTest() throws Exception {

		List<PizzaInfoDTO> pizzaInfoDTOList = new Gson().fromJson(
				new Gson().toJson(PizzaInfoListData.createPizzaInfoList()), new TypeToken<ArrayList<PizzaInfoDTO>>() {
				}.getType());
		when(pizzaInventory.addAndUpdatePizzaBatch(pizzaInfoDTOList)).thenReturn(PizzaShopConstants.SUCCESSFUL_OPERATION);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas/batch").content(new Gson().toJson(pizzaInfoDTOList))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void addPizzaInfoErrorTest() throws Exception {

		List<PizzaInfoDTO> pizzaInfoDTOList = null;
		when(pizzaInventory.addAndUpdatePizzaBatch(pizzaInfoDTOList)).thenThrow(
				new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Pizza list can not be empty."));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas/batch").content(new Gson().toJson(pizzaInfoDTOList))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	public void addPizzaInfoEmptyErrorTest() throws Exception {

		List<PizzaInfoDTO> pizzaInfoDTOList = new ArrayList<>();
		when(pizzaInventory.addAndUpdatePizzaBatch(pizzaInfoDTOList)).thenThrow(
				new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Pizza list can not be empty."));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/addUpdatePizza").content(new Gson().toJson(pizzaInfoDTOList))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andDo(print());
	}

	@Test
	public void getAllPizzInfoListTest() throws Exception {
		List<PizzaInfoDTO> pizzaInfoDTOList = new Gson().fromJson(
				new Gson().toJson(PizzaInfoListData.createPizzaInfoList()), new TypeToken<ArrayList<PizzaInfoDTO>>() {
				}.getType());
		when(pizzaInventory.getAllPizza()).thenReturn(pizzaInfoDTOList);
		
		mockMvc.perform(get("/api/pizzas").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

}
