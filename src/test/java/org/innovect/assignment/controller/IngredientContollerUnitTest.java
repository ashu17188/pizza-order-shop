package org.innovect.assignment.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.innovect.assignment.data.AdditionalStuffInfoListData;
import org.innovect.assignment.dto.AdditionalStuffInfoDTO;
import org.innovect.assignment.service.IngredientInventory;
import org.innovect.assignment.utils.PizzaShopConstants;
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
import com.google.gson.reflect.TypeToken;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IngredientContollerUnitTest {

	private MockMvc mockMvc;

	@Autowired
	private IngredientContoller ingredientContoller;

	@MockBean
	private IngredientInventory ingredientInventory;

	@Before
	public void setup() throws Exception {
		this.mockMvc = standaloneSetup(this.ingredientContoller).build();
	}

	@Test
	public void addAdditionalStuffTest() throws Exception {
		List<AdditionalStuffInfoDTO> additionalStuffDTOList = new Gson().fromJson(
				new Gson().toJson(AdditionalStuffInfoListData.createAdditionalStuffInfoList()),
				new TypeToken<ArrayList<AdditionalStuffInfoDTO>>() {
				}.getType());
		when(ingredientInventory.saveAndUpdateIngredientBatch(additionalStuffDTOList))
				.thenReturn(PizzaShopConstants.SUCCESSFUL_OPERATION);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/ingredients").content(new Gson().toJson(additionalStuffDTOList))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}


	@Test
	public void getAllStuffInfoTest() throws Exception {
		List<AdditionalStuffInfoDTO> additionalStuffDTOList = new Gson().fromJson(
				new Gson().toJson(AdditionalStuffInfoListData.createAdditionalStuffInfoList()),
				new TypeToken<ArrayList<AdditionalStuffInfoDTO>>() {
				}.getType());

		when(ingredientInventory.getAllIngredient()).thenReturn(additionalStuffDTOList);
		
		mockMvc.perform(get("/api/ingredients").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andReturn();
	}

}