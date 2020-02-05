package org.innovect.assignment.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.model.AdditionalStuffInfo;
import org.innovect.assignment.repository.AdditionalStuffRepository;
import org.innovect.assignment.service.IngredientInventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IngredientContollerUnitTest {

	private MockMvc mockMvc;

	@Autowired
	private IngredientContoller ingredientContoller;

	@MockBean
	private IngredientInventory ingredientInventory;

	@MockBean
	private AdditionalStuffRepository additionalStuffRepository;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.ingredientContoller).build();
	}

	@Test
	public void getAllIngredientTest() throws Exception {
		when(ingredientInventory.getAllIngredient()).thenReturn(createIngredientList());
		mockMvc.perform(get("/api/ingredients").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

	@Test
	public void getAllIngredientByIdTest() throws Exception {
		when(ingredientInventory.getIngredientById("Test1 Ingredient")).thenReturn(createIngredient());
		mockMvc.perform(get("/api/ingredients/Test1 Ingredient").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	@Test
	public void createNewIngredientTest() throws Exception {
		String payload = new Gson().toJson(createIngredient());
		AdditionalStuffInfoDTO additionalStuffInfoDTO = createIngredient();
		when(ingredientInventory.saveAndUpdateIngredient(additionalStuffInfoDTO)).thenReturn(additionalStuffInfoDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/ingredients").content(payload)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	public void addAdditionalStuffTest() throws Exception {
		List<AdditionalStuffInfoDTO> ingredientList = createIngredientList();
		AdditionalStuffInfo obj1 = new Gson().fromJson(new Gson().toJson(ingredientList.get(0)),
				AdditionalStuffInfo.class);
		AdditionalStuffInfo obj2 = new Gson().fromJson(new Gson().toJson(ingredientList.get(1)),
				AdditionalStuffInfo.class);

		when(additionalStuffRepository.findByStuffName(ingredientList.get(0).getStuffName())).thenReturn(obj1);
		when(additionalStuffRepository.findByStuffName(ingredientList.get(1).getStuffName())).thenReturn(obj2);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/ingredients/batch").content(new Gson().toJson(ingredientList))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
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