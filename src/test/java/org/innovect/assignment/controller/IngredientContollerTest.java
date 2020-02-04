package org.innovect.assignment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
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
public class IngredientContollerTest {

	private MockMvc mockMvc;

	@Autowired
	private IngredientContoller ingredientContoller;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.ingredientContoller).build();
	}

	@Test
	public void getAllIngredientTest() throws Exception {
		mockMvc.perform(get("/api/ingredients").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

	@Test
	public void getAllIngredientByIdTest() throws Exception {
		mockMvc.perform(get("/api/ingredients/Cheese Burst").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	public void createNewIngredientTest() throws Exception {
		String payload = new Gson().toJson(createIngredient());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/ingredients").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	public void updateIngredientTest() throws Exception {
		AdditionalStuffInfoDTO objForUpdate = createIngredient();
		String payload = new Gson().toJson(objForUpdate);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/ingredients").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
		objForUpdate.setStockQuantity(20);
		payload = new Gson().toJson(objForUpdate);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/ingredients").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void deleteIngredientByIdTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
	            .delete("/api/ingredients/New hand tossed").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}
	
	@Test
	public void batchCreateUpdateIngredientTest() throws Exception {
		String payload = new Gson().toJson(createIngredientList());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/ingredients/batch").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}
	
	private AdditionalStuffInfoDTO createIngredient() {
		return new AdditionalStuffInfoDTO("Test1 Ingredient", "Veg Toppings", 10.00, 10);
	}

	private List<AdditionalStuffInfoDTO> createIngredientList() {
		List<AdditionalStuffInfoDTO> list = new ArrayList<>();
		list.add(new AdditionalStuffInfoDTO("Test11 Ingredient", "Veg Toppings", 11.00, 11));
		list.add(new AdditionalStuffInfoDTO("Test12 Ingredient", "Non-­Veg Toppings", 12.00, 12));
		list.add(new AdditionalStuffInfoDTO("Test14 Ingredient", "crust", 14.00, 14));
		return list;
	}

}