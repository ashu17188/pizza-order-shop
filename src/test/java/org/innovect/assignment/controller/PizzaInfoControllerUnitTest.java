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
import org.innovect.assignment.repository.PizzaInfoRepository;
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
	private PizzaInfoController pizzaInfoController;

	@MockBean
	private PizzaInventory pizzaInventory;

	@MockBean
	private PizzaInfoRepository pizzaInfoRepository;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.pizzaInfoController).build();
	}

	@Test
	public void getAllPizzaTest() throws Exception {
		when(pizzaInventory.getAllPizza()).thenReturn(createPizzaDTOList());
		mockMvc.perform(get("/api/pizzas").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

	@Test
	public void getAllPizzaByIdTest() throws Exception {
		when(pizzaInventory.getPizzaById(1)).thenReturn(createPizza());
		mockMvc.perform(get("/api/pizzas/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

	@Test
	public void getAllPizzaByNonExistIdTest() throws Exception {
		mockMvc.perform(get("/api/pizzas/2222").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(404))
				.andDo(print()).andReturn();
	}

	@Test
	public void addPizzaTest() throws Exception {
		String payload = new Gson().toJson(createPizza());
		when(pizzaInventory.saveAndUpdatePizza(createPizza())).thenReturn(createPizza());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	public void addNullPizzaTest() throws Exception {
		when(pizzaInventory.saveAndUpdatePizza(null)).thenReturn(createPizza());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas").content(new String())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().is(400))
				.andDo(print());
	}

	@Test
	public void addBlankPizzaTest() throws Exception {
		when(pizzaInventory.saveAndUpdatePizza(null)).thenReturn(new PizzaInfoDTO());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas").content(new String())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().is(400))
				.andDo(print());
	}

	@Test
	public void addPizzaInfoTest() throws Exception {

		List<PizzaInfoDTO> pizzaInfoDTOList = new Gson().fromJson(
				new Gson().toJson(PizzaInfoListData.createPizzaInfoList()), new TypeToken<ArrayList<PizzaInfoDTO>>() {
				}.getType());
		when(pizzaInventory.addAndUpdatePizzaBatch(pizzaInfoDTOList))
				.thenReturn(PizzaShopConstants.SUCCESSFUL_OPERATION);

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
	public void addPizzaInfoEmptyListErrorTest() throws Exception {
		List<PizzaInfoDTO> pizzaInfoDTOList = new ArrayList<>();
		when(pizzaInventory.addAndUpdatePizzaBatch(pizzaInfoDTOList)).thenThrow(
				new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Pizza list can not be empty."));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/pizzas").content(new Gson().toJson(pizzaInfoDTOList))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().is(400))
				.andDo(print());
	}

	private PizzaInfoDTO createPizza() {
		return new PizzaInfoDTO(1, "Test Dev21", "Vegetarian", "Regular", 200.00, 50);
	}

	private List<PizzaInfoDTO> createPizzaDTOList() {
		List<PizzaInfoDTO> pizzaList = new ArrayList<>();
		pizzaList.add(new PizzaInfoDTO(0, "Test Dev21", "Vegetarian", "Regular", 200.00, 50));
		pizzaList.add(new PizzaInfoDTO(0, "Test Dev22", "Non Vegetarian", "Large", 350.00, 40));
		return pizzaList;
	}
}
